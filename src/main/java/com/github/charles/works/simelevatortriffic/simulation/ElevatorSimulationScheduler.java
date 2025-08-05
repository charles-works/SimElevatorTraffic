package com.github.charles.works.simelevatortriffic.simulation;

import com.github.charles.works.simelevatortriffic.domain.*;
import com.github.charles.works.simelevatortriffic.service.ServiceHelpers.*;

/**
 * 电梯交通仿真调度器，实现离散事件仿真
 * "支持实时仿真(1秒模拟时间≤100ms真实时间)"
 */
public class ElevatorSimulationScheduler {
    
    private final java.util.PriorityQueue<SimulationEvent> eventQueue = new java.util.PriorityQueue<>(
        java.util.Comparator.comparingLong(SimulationEvent::getTimestamp)
    );
    
    private final Building building;
    private final com.github.charles.works.simelevatortriffic.config.SimulationConfig config;
    private final SimulationClock clock;
    
    private SimulationState currentState;
    
    public ElevatorSimulationScheduler(
        Building building, 
        com.github.charles.works.simelevatortriffic.config.SimulationConfig config,
        SimulationClock clock
    ) {
        this.building = building;
        this.config = config;
        this.clock = clock;
        this.currentState = new SimulationState(building);
    }
    
    /**
     * 初始化仿真，添加初始事件
     */
    public void initialize() {
        // 添加初始乘客到达事件
        java.util.List<PassengerArrivalEvent> initialEvents = generateInitialPassengerFlow();
        eventQueue.addAll(initialEvents);
        
        // 添加电梯初始化事件
        for (ElevatorGroup group : building.getElevatorGroups()) {
            for (Elevator elevator : group.getElevators()) {
                eventQueue.add(new ElevatorInitializationEvent(
                    clock.getCurrentTime(),
                    building.getId(),
                    elevator.getId()
                ));
            }
        }
    }
    
    /**
     * 运行仿真直到指定时间
     */
    public SimulationResult runUntil(long endTime) {
        long startTime = System.currentTimeMillis();
        
        while (!eventQueue.isEmpty() && clock.getCurrentTime() < endTime) {
            SimulationEvent event = eventQueue.poll();
            
            // 更新仿真时钟
            clock.setCurrentTime(event.getTimestamp());
            
            // 处理事件
            handleEvent(event);
            
            // 记录时间切片数据(如果需要)
            if (shouldRecordTimeline()) {
                recordTimelineSnapshot();
            }
        }
        
        // 生成仿真结果
        return generateSimulationResult(startTime);
    }
    
    /**
     * 处理仿真事件
     */
    private void handleEvent(SimulationEvent event) {
        if (event instanceof PassengerArrivalEvent arrivalEvent) {
            handlePassengerArrival(arrivalEvent);
        } 
        else if (event instanceof CallRegistrationEvent callEvent) {
            handleCallRegistration(callEvent);
        }
        else if (event instanceof ElevatorStopEvent stopEvent) {
            handleElevatorStop(stopEvent);
        }
        else if (event instanceof PassengerBoardingEvent boardingEvent) {
            handlePassengerBoarding(boardingEvent);
        }
        else if (event instanceof PassengerAlightingEvent alightingEvent) {
            handlePassengerAlighting(alightingEvent);
        }
        else if (event instanceof ElevatorDirectionChangeEvent directionEvent) {
            handleElevatorDirectionChange(directionEvent);
        }
        else if (event instanceof ElevatorInitializationEvent initEvent) {
            handleElevatorInitialization(initEvent);
        }
        // 其他事件类型...
    }
    
    /**
     * 处理乘客到达事件
     */
    private void handlePassengerArrival(PassengerArrivalEvent event) {
        // 更新状态
        currentState.recordPassengerArrival(event);
        
        // 根据控制类型处理召唤请求
        for (Passenger passenger : event.getPassengers()) {
            CallRequest request = createCallRequest(passenger);
            
            // 对于目的选层系统，乘客先输入目的地
            if (isDestinationControlSystem(event.getFloor())) {
                handleDestinationRequest(request);
            } 
            // 对于传统系统，乘客直接注册召唤
            else {
                handleStandardCallRequest(request);
            }
        }
        
        // 生成后续事件(如果需要)
        generateFollowUpEvents(event);
    }
    
    /**
     * 处理电梯停靠事件
     */
    private void handleElevatorStop(ElevatorStopEvent event) {
        Elevator elevator = getElevator(event.getElevatorId());
        Floor floor = building.getFloor(event.getFloor());
        
        // 更新电梯状态
        ElevatorStatus newStatus = elevator.getStatus().withDoorOpen(true);
        elevator = elevator.withStatus(newStatus);
        
        // 记录停靠开始时间
        long stopStartTime = clock.getCurrentTime();
        
        // 计算乘客流动时间
        double boardingTime = calculateBoardingTime(
            event.getBoardingCount(), 
            elevator.getDoorWidth()
        );
        double alightingTime = calculateAlightingTime(
            event.getAlightingCount(), 
            elevator.getDoorWidth()
        );
        
        // 确定总停站时间
        long stopDuration = determineStopDuration(
            boardingTime, 
            alightingTime,
            elevator
        );
        
        // 创建停站结束事件
        eventQueue.add(new ElevatorStopCompletionEvent(
            clock.getCurrentTime() + stopDuration,
            building.getId(),
            elevator.getId(),
            event.getFloor(),
            stopStartTime
        ));
        
        // 更新状态
        currentState.updateElevatorStop(event, stopDuration);
    }
    
    /**
     * 计算乘客流动时间，考虑开门宽度影响
     * "同一层站如上下乘客较多，受开门宽度影响会增加电梯停靠等待时间"
     */
    private double calculateBoardingTime(int passengerCount, double doorWidth) {
        if (passengerCount == 0) return 0;
        
        // 基础上车时间(每乘客)
        double baseTimePerPassenger = 1.2; // 秒
        
        // 门宽影响系数(门越宽，流动越快)
        double widthFactor = 1.0 / (0.8 + 0.4 * (doorWidth / 1.2));
        
        // 拥挤系数(乘客越多，效率越低)
        double congestionFactor = 1.0 + 0.2 * Math.log(1 + passengerCount);
        
        return passengerCount * baseTimePerPassenger * widthFactor * congestionFactor;
    }
    
    // 其他辅助方法...
    
    /**
     * 生成初始乘客流
     * 根据建筑类型和时间模式生成初始乘客到达事件
     */
    private java.util.List<PassengerArrivalEvent> generateInitialPassengerFlow() {
        java.util.List<PassengerArrivalEvent> events = new java.util.ArrayList<>();
        // 简化实现：生成一些测试事件
        // 实际实现应该基于建筑类型、流量模式和配置来生成
        return events;
    }
    
    /**
     * 判断是否应该记录时间线快照
     * 根据配置的时间片间隔决定是否记录
     */
    private boolean shouldRecordTimeline() {
        // 简化实现：根据配置决定是否记录时间线
        return config.getTimeSlice() > 0 && 
               (clock.getCurrentTime() % config.getTimeSlice()) == 0;
    }
    
    /**
     * 记录时间线快照
     * 保存当前仿真状态的快照用于后续分析
     */
    private void recordTimelineSnapshot() {
        // 简化实现：记录当前时间点的仿真状态快照
        // 实际实现应该保存电梯状态、乘客分布等信息
    }
    
    /**
     * 生成仿真结果
     * 根据仿真过程中的数据生成最终结果
     */
    private SimulationResult generateSimulationResult(long startTime) {
        long endTime = clock.getCurrentTime();
        long realDuration = System.currentTimeMillis() - startTime;
        String simulationId = java.util.UUID.randomUUID().toString();
        
        return new SimulationResult(simulationId, endTime - realDuration, endTime);
    }
    
    /**
     * 处理召唤注册事件
     * 根据电梯控制类型分配电梯并注册停靠请求
     */
    private void handleCallRegistration(CallRegistrationEvent callEvent) {
        // 更新状态
        // 实际实现应该根据控制类型分配电梯并注册停靠请求
        // 对于群控系统，需要复杂的调度算法
        // 对于目的选层系统，需要将请求发送到大厅的输入面板
    }
    
    /**
     * 处理乘客上车事件
     * 更新电梯和乘客状态，可能生成新的事件
     */
    private void handlePassengerBoarding(PassengerBoardingEvent boardingEvent) {
        // 更新状态
        // 实际实现应该：
        // 1. 更新电梯状态（乘客列表、负载等）
        // 2. 更新乘客状态（上车时间）
        // 3. 可能需要生成新的事件（如电梯满载后关门事件）
    }
    
    /**
     * 处理乘客下车事件
     * 更新电梯和乘客状态，可能生成新的事件
     */
    private void handlePassengerAlighting(PassengerAlightingEvent alightingEvent) {
        // 更新状态
        // 实际实现应该：
        // 1. 更新电梯状态（乘客列表、负载等）
        // 2. 更新乘客状态（下车时间）
        // 3. 记录乘客旅程完成数据
        // 4. 可能需要生成新的事件（如所有乘客下车后关门事件）
    }
    
    /**
     * 处理电梯方向改变事件
     * 更新电梯方向状态并可能重新计算路径
     */
    private void handleElevatorDirectionChange(ElevatorDirectionChangeEvent directionEvent) {
        // 更新状态
        // 实际实现应该：
        // 1. 更新电梯方向状态
        // 2. 根据新的方向重新计算路径
        // 3. 可能需要调整停靠序列
    }
    
    /**
     * 处理电梯初始化事件
     * 设置电梯初始状态并可能生成初始事件
     */
    private void handleElevatorInitialization(ElevatorInitializationEvent initEvent) {
        // 更新状态
        // 实际实现应该：
        // 1. 设置电梯初始状态（位置、方向、门状态等）
        // 2. 可能生成初始事件（如空闲事件）
    }
    
    /**
     * 为乘客创建召唤请求
     * 根据乘客的起始楼层和目标楼层确定召唤方向
     */
    private com.github.charles.works.simelevatortriffic.service.ServiceHelpers.CallRequest createCallRequest(Passenger passenger) {
        int originFloor = passenger.getOriginFloor();
        int destinationFloor = passenger.getDestinationFloor();
        ElevatorDirection direction = originFloor < destinationFloor ? 
            ElevatorDirection.UP : ElevatorDirection.DOWN;
        
        return new com.github.charles.works.simelevatortriffic.service.ServiceHelpers.CallRequest(
            originFloor, direction, passenger.getId());
    }
    
    /**
     * 判断指定楼层是否使用目的选层系统
     * 根据电梯组控制类型判断
     */
    private boolean isDestinationControlSystem(int floor) {
        // 查找服务该楼层的电梯组
        for (ElevatorGroup group : building.getElevatorGroups()) {
            if (group.getServedFloors().contains(floor)) {
                return group.getControlType() == ElevatorControlType.DESTINATION_CONTROL;
            }
        }
        return false;
    }
    
    /**
     * 处理目的选层系统请求
     * 在目的选层系统中，乘客在大厅输入面板上选择目标楼层
     */
    private void handleDestinationRequest(CallRequest request) {
        // 实际实现应该：
        // 1. 将请求发送到目的选层分配系统
        // 2. 分配合适的电梯给乘客
        // 3. 向乘客显示分配结果（电梯和预计等待时间）
        // 4. 在分配的电梯中注册目标楼层
    }
    
    /**
     * 处理传统召唤请求
     * 在传统系统中，乘客直接在楼层按钮上注册上下行请求
     */
    private void handleStandardCallRequest(CallRequest request) {
        // 实际实现应该：
        // 1. 根据请求方向和楼层寻找合适的电梯
        // 2. 在电梯中注册停靠请求
        // 3. 可能需要生成事件（如电梯接近事件）
    }
    
    /**
     * 生成后续事件
     * 根据当前事件生成可能的后续事件
     */
    private void generateFollowUpEvents(PassengerArrivalEvent event) {
        // 实际实现应该：
        // 1. 根据乘客流量模式生成后续乘客到达事件
        // 2. 可能生成召唤注册事件
        // 3. 根据时间模式调整生成频率
    }
    
    /**
     * 根据ID获取电梯对象
     * 在建筑的所有电梯组中查找指定ID的电梯
     */
    private Elevator getElevator(String elevatorId) {
        for (ElevatorGroup group : building.getElevatorGroups()) {
            for (Elevator elevator : group.getElevators()) {
                if (elevator.getId().equals(elevatorId)) {
                    return elevator;
                }
            }
        }
        return null;
    }
    
    /**
     * 计算乘客下车时间，考虑开门宽度影响
     * "同一层站如上下乘客较多，受开门宽度影响会增加电梯停靠等待时间"
     */
    private double calculateAlightingTime(int alightingCount, double doorWidth) {
        if (alightingCount == 0) return 0;
        
        // 基础下车时间(每乘客)
        double baseTimePerPassenger = 1.0; // 秒
        
        // 门宽影响系数(门越宽，流动越快)
        double widthFactor = 1.0 / (0.8 + 0.4 * (doorWidth / 1.2));
        
        // 拥挤系数(乘客越多，效率越低)
        double congestionFactor = 1.0 + 0.15 * Math.log(1 + alightingCount);
        
        return alightingCount * baseTimePerPassenger * widthFactor * congestionFactor;
    }
    
    /**
     * 确定电梯停站总时间
     * 综合考虑上下车时间和电梯门操作时间
     */
    private long determineStopDuration(double boardingTime, double alightingTime, Elevator elevator) {
        // 门开关时间(秒)
        double doorOperationTime = 2.0;
        
        // 总停站时间(秒)
        double totalStopTime = doorOperationTime + Math.max(boardingTime, alightingTime);
        
        // 转换为毫秒并向上取整
        return (long) Math.ceil(totalStopTime * 1000);
    }
}

// 辅助类定义
class SimulationClock {
    private long currentTime;
    
    public SimulationClock(long startTime) {
        this.currentTime = startTime;
    }
    
    public long getCurrentTime() {
        return currentTime;
    }
    
    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }
}

class SimulationState {
    private final Building building;
    // 状态字段和方法
    
    public SimulationState(Building building) {
        this.building = building;
    }
    
    public void recordPassengerArrival(PassengerArrivalEvent event) {
        // 记录乘客到达事件，更新相关统计数据
        // 实际实现应该更新乘客流量统计、等待时间等信息
    }
    
    public void updateElevatorStop(ElevatorStopEvent event, long stopDuration) {
        // 更新电梯停靠状态，记录停靠时间和乘客流动数据
        // 实际实现应该更新电梯利用率、乘客旅程时间等信息
    }
}

class SimulationResult {
    private final String simulationId;
    private final long startTime;
    private final long endTime;
    // 结果字段和方法
    
    public SimulationResult() {
        this.simulationId = "";
        this.startTime = 0L;
        this.endTime = 0L;
    }
    
    public SimulationResult(String simulationId, long startTime, long endTime) {
        this.simulationId = simulationId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    // getter方法
    public String getSimulationId() { return simulationId; }
    public long getStartTime() { return startTime; }
    public long getEndTime() { return endTime; }
    
    public long getDuration() { return endTime - startTime; }
}