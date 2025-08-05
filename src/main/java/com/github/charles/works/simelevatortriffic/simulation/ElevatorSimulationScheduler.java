package com.github.charles.works.simelevatortriffic.simulation;

import com.github.charles.works.simelevatortriffic.domain.*;
import com.github.charles.works.simelevatortriffic.service.ServiceHelpers.*;
import java.util.List;

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
    public SimulationResultInternal runUntil(long endTime) {
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
    
    /**
     * 查找服务指定楼层的电梯组
     */
    private ElevatorGroup findElevatorGroupForFloor(int floorNumber) {
        for (ElevatorGroup group : building.getElevatorGroups()) {
            if (group.getServedFloors().contains(floorNumber)) {
                return group;
            }
        }
        return null;
    }
    
    /**
     * 处理群控系统的召唤请求
     */
    private void handleGroupControlCall(CallRegistrationEvent callEvent, ElevatorGroup group) {
        // 简化实现：寻找最优电梯
        // 实际实现应该使用更复杂的群控算法
        Elevator bestElevator = selectBestElevatorForGroupControl(callEvent, group);
        if (bestElevator != null) {
            registerStopWithElevator(bestElevator, callEvent.getFloor(), callEvent.getDirection());
        }
    }
    
    /**
     * 处理目的选层系统的召唤请求
     */
    private void handleDestinationControlCall(CallRegistrationEvent callEvent, ElevatorGroup group) {
        // 在目的选层系统中，乘客需要先输入目标楼层
        // 这里简化处理，假设乘客已输入目标楼层
        // 实际实现应该有更复杂的逻辑
    }
    
    /**
     * 处理传统系统的召唤请求
     */
    private void handleStandardCall(CallRegistrationEvent callEvent, ElevatorGroup group) {
        // 寻找最近的电梯
        Elevator nearestElevator = findNearestElevator(callEvent.getFloor(), callEvent.getDirection(), group);
        if (nearestElevator != null) {
            registerStopWithElevator(nearestElevator, callEvent.getFloor(), callEvent.getDirection());
        }
    }
    
    /**
     * 在电梯中注册停靠请求
     */
    private void registerStopWithElevator(Elevator elevator, int floor, ElevatorDirection direction) {
        ElevatorStatus status = elevator.getStatus();
        java.util.Set<Integer> updatedStops = new java.util.HashSet<>(status.getRegisteredStops());
        updatedStops.add(floor);
        
        ElevatorStatus newStatus = new ElevatorStatus(
            status.getPosition(),
            status.getDirection(),
            status.getSpeed(),
            status.isDoorOpen(),
            status.getDoorOpenTime(),
            updatedStops,
            status.getPassengers(),
            status.getLastStopTime(),
            status.getBoardingPassengers(),
            status.getAlightingPassengers()
        );
        
        // 更新电梯状态（实际实现中应通过不可变对象更新）
        // elevator.setStatus(newStatus);
    }
    
    /**
     * 在电梯中注册目标楼层（用于目的选层系统）
     */
    private void registerDestinationWithElevator(Elevator elevator, int floor, ElevatorDirection direction) {
        // 类似于registerStopWithElevator，但在目的选层系统中有不同处理
        registerStopWithElevator(elevator, floor, direction);
    }
    
    /**
     * 计算预期等待时间
     */
    private double calculateExpectedWaitTime(Elevator elevator, int floor) {
        // 简化实现：基于电梯当前位置和速度估算
        // 实际实现应该考虑更多因素
        ElevatorStatus status = elevator.getStatus();
        double currentPosition = status.getPosition();
        double targetPosition = floor * building.getFloorHeight();
        double distance = Math.abs(targetPosition - currentPosition);
        
        // 简单估算：距离/速度 + 一些固定时间
        return distance / elevator.getRatedSpeed() + 5.0; // 加5秒固定时间
    }
    
    /**
     * 选择群控系统中最优电梯
     */
    private Elevator selectBestElevatorForGroupControl(CallRegistrationEvent callEvent, ElevatorGroup group) {
        // 简化实现：选择第一个可用电梯
        // 实际实现应该使用更复杂的群控算法
        for (Elevator elevator : group.getElevators()) {
            if (elevator.getStatus().getDirection() == ElevatorDirection.IDLE || 
                elevator.getStatus().getDirection() == callEvent.getDirection()) {
                return elevator;
            }
        }
        return group.getElevators().isEmpty() ? null : group.getElevators().get(0);
    }
    
    /**
     * 查找最近的电梯
     */
    private Elevator findNearestElevator(int floor, ElevatorDirection direction, ElevatorGroup group) {
        Elevator nearest = null;
        double minDistance = Double.MAX_VALUE;
        double targetPosition = floor * building.getFloorHeight();
        
        for (Elevator elevator : group.getElevators()) {
            double currentPosition = elevator.getStatus().getPosition();
            double distance = Math.abs(targetPosition - currentPosition);
            
            if (distance < minDistance) {
                minDistance = distance;
                nearest = elevator;
            }
        }
        
        return nearest;
    }
    
    /**
     * 重新计算电梯路径
     */
    private void recalculateElevatorPath(Elevator elevator) {
        // 简化实现：实际实现应该根据电梯的新方向重新排序停靠楼层
        // 这通常涉及复杂的路径规划算法
    }
    
    /**
     * 调整停靠序列
     */
    private void adjustStopSequence(Elevator elevator) {
        // 简化实现：实际实现应该根据电梯当前方向和位置调整停靠顺序
    }
    
    /**
     * 计算下一次乘客到达的时间间隔
     */
    private long calculateNextArrivalInterval() {
        // 简化实现：固定时间间隔
        // 实际实现应该基于流量模式和统计分布
        return 5000; // 5秒
    }
    
    /**
     * 为下一个事件生成乘客
     */
    private java.util.List<Passenger> generatePassengersForNextEvent(int floor) {
        // 简化实现：生成固定数量的乘客
        // 实际实现应该基于流量模式和统计分布
        java.util.List<Passenger> passengers = new java.util.ArrayList<>();
        int count = 1 + (int)(Math.random() * 5); // 1-5个乘客
        
        for (int i = 0; i < count; i++) {
            passengers.add(new Passenger(
                java.util.UUID.randomUUID().toString(),
                floor,
                (int)(Math.random() * building.getTotalFloors()),
                clock.getCurrentTime(),
                0L, // boardingTime
                0L, // alightingTime
                com.github.charles.works.simelevatortriffic.domain.PassengerType.INDIVIDUAL,
                1 // batchSize
            ));
        }
        
        return passengers;
    }
    
    /**
     * 生成初始乘客
     */
    private java.util.List<Passenger> generateInitialPassengers(int floor) {
        // 简化实现：生成固定数量的乘客
        java.util.List<Passenger> passengers = new java.util.ArrayList<>();
        int count = 2 + (int)(Math.random() * 3); // 2-4个乘客
        
        for (int i = 0; i < count; i++) {
            passengers.add(new Passenger(
                java.util.UUID.randomUUID().toString(),
                floor,
                (int)(Math.random() * building.getTotalFloors()),
                clock.getCurrentTime(),
                0L, // boardingTime
                0L, // alightingTime
                com.github.charles.works.simelevatortriffic.domain.PassengerType.INDIVIDUAL,
                1 // batchSize
            ));
        }
        
        return passengers;
    }
    
    /**
     * 捕获电梯状态
     */
    private java.util.List<com.github.charles.works.simelevatortriffic.domain.ElevatorStatusSnapshot> captureElevatorStates() {
        java.util.List<com.github.charles.works.simelevatortriffic.domain.ElevatorStatusSnapshot> states = 
            new java.util.ArrayList<>();
        
        for (ElevatorGroup group : building.getElevatorGroups()) {
            for (Elevator elevator : group.getElevators()) {
                ElevatorStatus status = elevator.getStatus();
                states.add(new com.github.charles.works.simelevatortriffic.domain.ElevatorStatusSnapshot(
                    elevator.getId(),
                    status.getPosition(),
                    status.getDirection(),
                    status.getSpeed(),
                    status.isDoorOpen(),
                    status.getPassengers().size()
                ));
            }
        }
        
        return states;
    }
    
    /**
     * 捕获乘客分布
     */
    private com.github.charles.works.simelevatortriffic.domain.PassengerDistribution capturePassengerDistribution() {
        // 简化实现：返回空的乘客分布
        return new com.github.charles.works.simelevatortriffic.domain.PassengerDistribution(
            new java.util.HashMap<>(),
            new java.util.HashMap<>()
        );
    }
    
    /**
     * 收集电梯统计数据
     */
    private java.util.List<com.github.charles.works.simelevatortriffic.domain.ElevatorStatistics> collectElevatorStatistics() {
        java.util.List<com.github.charles.works.simelevatortriffic.domain.ElevatorStatistics> stats = 
            new java.util.ArrayList<>();
        
        for (ElevatorGroup group : building.getElevatorGroups()) {
            for (Elevator elevator : group.getElevators()) {
                // 简化实现：生成基本统计数据
                stats.add(new com.github.charles.works.simelevatortriffic.domain.ElevatorStatistics(
                    elevator.getId(),
                    0, // totalTrips
                    0.0, // averageWaitTime
                    0.0, // averageTravelTime
                    0.0 // utilizationRate
                ));
            }
        }
        
        return stats;
    }
    
    /**
     * 收集乘客统计数据
     */
    private com.github.charles.works.simelevatortriffic.domain.PassengerStatistics collectPassengerStatistics() {
        // 简化实现：返回基本的乘客统计数据
        return new com.github.charles.works.simelevatortriffic.domain.PassengerStatistics(
            0, // totalPassengers
            0.0, // averageWaitTime
            0.0, // averageTravelTime
            0 // maxWaitTime
        );
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
        
        // 为每个楼层生成初始事件
        for (Floor floor : building.getFloors()) {
            // 根据楼层使用情况生成乘客到达事件
            int population = floor.getPopulation();
            if (population > 0) {
                // 为每层楼生成一个初始乘客到达事件
                PassengerArrivalEvent event = new PassengerArrivalEvent(
                    clock.getCurrentTime() + 1000, // 1秒后开始
                    building.getId(),
                    floor.getFloorNumber(),
                    generateInitialPassengers(floor.getFloorNumber())
                );
                events.add(event);
            }
        }
        
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
        
        // 创建时间线快照对象
        com.github.charles.works.simelevatortriffic.domain.TimelineSnapshot snapshot = 
            new com.github.charles.works.simelevatortriffic.domain.TimelineSnapshot(
                clock.getCurrentTime(),
                captureElevatorStates(),
                capturePassengerDistribution()
            );
        
        // 更新状态
        currentState.recordTimelineSnapshot(snapshot);
    }
    
    /**
     * 生成仿真结果
     * 根据仿真过程中的数据生成最终结果
     */
    private SimulationResultInternal generateSimulationResult(long startTime) {
        long endTime = clock.getCurrentTime();
        long realDuration = System.currentTimeMillis() - startTime;
        String simulationId = java.util.UUID.randomUUID().toString();
        
        // 收集电梯统计数据
        java.util.List<com.github.charles.works.simelevatortriffic.domain.ElevatorStatistics> elevatorStats = 
            collectElevatorStatistics();
        
        // 收集乘客统计数据
        com.github.charles.works.simelevatortriffic.domain.PassengerStatistics passengerStats = 
            collectPassengerStatistics();
        
        return new SimulationResultInternal(simulationId, startTime, endTime, realDuration, elevatorStats, passengerStats);
    }
    
    /**
     * 处理召唤注册事件
     * 根据电梯控制类型分配电梯并注册停靠请求
     */
    private void handleCallRegistration(CallRegistrationEvent callEvent) {
        // 更新状态
        currentState.recordCallRegistration(callEvent);
        
        // 根据电梯控制类型分配电梯并注册停靠请求
        ElevatorGroup group = findElevatorGroupForFloor(callEvent.getFloor());
        if (group != null) {
            switch (group.getControlType()) {
                case GROUP_CONTROL:
                    // 对于群控系统，需要复杂的调度算法
                    handleGroupControlCall(callEvent, group);
                    break;
                case DESTINATION_CONTROL:
                    // 对于目的选层系统，需要将请求发送到大厅的输入面板
                    handleDestinationControlCall(callEvent, group);
                    break;
                default:
                    // 对于传统系统，直接分配最近的电梯
                    handleStandardCall(callEvent, group);
                    break;
            }
        }
    }
    
    /**
     * 处理乘客上车事件
     * 更新电梯和乘客状态，可能生成新的事件
     */
    private void handlePassengerBoarding(PassengerBoardingEvent boardingEvent) {
        // 更新状态
        currentState.recordPassengerBoarding(boardingEvent);
        
        // 获取电梯对象
        Elevator elevator = getElevator(boardingEvent.getElevatorId());
        if (elevator == null) return;
        
        // 更新电梯状态（乘客列表、负载等）
        ElevatorStatus status = elevator.getStatus();
        List<Passenger> updatedPassengers = new java.util.ArrayList<>(status.getPassengers());
        
        // 添加上车乘客
        for (Passenger passenger : boardingEvent.getPassengers()) {
            updatedPassengers.add(passenger);
        }
        
        // 创建新的电梯状态
        ElevatorStatus newStatus = new ElevatorStatus(
            status.getPosition(),
            status.getDirection(),
            status.getSpeed(),
            status.isDoorOpen(),
            status.getDoorOpenTime(),
            status.getRegisteredStops(),
            updatedPassengers,
            status.getLastStopTime(),
            status.getBoardingPassengers(),
            status.getAlightingPassengers()
        );
        
        // 更新电梯状态
        elevator = elevator.withStatus(newStatus);
        
        // 更新乘客状态（上车时间）
        for (Passenger passenger : boardingEvent.getPassengers()) {
            // 在实际实现中，可能需要更新乘客对象的上车时间
            // 这里简化处理，因为Passenger是不可变对象
        }
        
        // 如果电梯满载，生成关门事件
        if (updatedPassengers.size() >= elevator.getCapacity()) {
            eventQueue.add(new com.github.charles.works.simelevatortriffic.domain.ElevatorDoorCloseEvent(
                clock.getCurrentTime() + 1000, // 1秒后关门
                building.getId(),
                elevator.getId(),
                boardingEvent.getFloor()
            ));
        }
    }
    
    /**
     * 处理乘客下车事件
     * 更新电梯和乘客状态，可能生成新的事件
     */
    private void handlePassengerAlighting(PassengerAlightingEvent alightingEvent) {
        // 更新状态
        currentState.recordPassengerAlighting(alightingEvent);
        
        // 获取电梯对象
        Elevator elevator = getElevator(alightingEvent.getElevatorId());
        if (elevator == null) return;
        
        // 更新电梯状态（乘客列表、负载等）
        ElevatorStatus status = elevator.getStatus();
        List<Passenger> updatedPassengers = new java.util.ArrayList<>(status.getPassengers());
        
        // 移除下车乘客
        updatedPassengers.removeIf(passenger -> 
            alightingEvent.getPassengerIds().contains(passenger.getId()));
        
        // 创建新的电梯状态
        ElevatorStatus newStatus = new ElevatorStatus(
            status.getPosition(),
            status.getDirection(),
            status.getSpeed(),
            status.isDoorOpen(),
            status.getDoorOpenTime(),
            status.getRegisteredStops(),
            updatedPassengers,
            status.getLastStopTime(),
            status.getBoardingPassengers(),
            status.getAlightingPassengers()
        );
        
        // 更新电梯状态
        elevator = elevator.withStatus(newStatus);
        
        // 记录乘客旅程完成数据
        for (String passengerId : alightingEvent.getPassengerIds()) {
            // 在实际实现中，可能需要记录乘客的旅程时间等统计数据
            // 这里简化处理
        }
        
        // 如果所有乘客都已下车，生成关门事件
        if (updatedPassengers.isEmpty()) {
            eventQueue.add(new com.github.charles.works.simelevatortriffic.domain.ElevatorDoorCloseEvent(
                clock.getCurrentTime() + 1000, // 1秒后关门
                building.getId(),
                elevator.getId(),
                alightingEvent.getFloor()
            ));
        }
    }
    
    /**
     * 处理电梯方向改变事件
     * 更新电梯方向状态并可能重新计算路径
     */
    private void handleElevatorDirectionChange(ElevatorDirectionChangeEvent directionEvent) {
        // 更新状态
        currentState.recordElevatorDirectionChange(directionEvent);
        
        // 获取电梯对象
        Elevator elevator = getElevator(directionEvent.getElevatorId());
        if (elevator == null) return;
        
        // 更新电梯方向状态
        ElevatorStatus status = elevator.getStatus();
        ElevatorStatus newStatus = new ElevatorStatus(
            status.getPosition(),
            directionEvent.getNewDirection(),
            status.getSpeed(),
            status.isDoorOpen(),
            status.getDoorOpenTime(),
            status.getRegisteredStops(),
            status.getPassengers(),
            status.getLastStopTime(),
            status.getBoardingPassengers(),
            status.getAlightingPassengers()
        );
        
        // 更新电梯状态
        elevator = elevator.withStatus(newStatus);
        
        // 根据新的方向重新计算路径
        recalculateElevatorPath(elevator);
        
        // 可能需要调整停靠序列
        adjustStopSequence(elevator);
    }
    
    /**
     * 处理电梯初始化事件
     * 设置电梯初始状态并可能生成初始事件
     */
    private void handleElevatorInitialization(ElevatorInitializationEvent initEvent) {
        // 更新状态
        currentState.recordElevatorInitialization(initEvent);
        
        // 获取电梯对象
        Elevator elevator = getElevator(initEvent.getElevatorId());
        if (elevator == null) return;
        
        // 设置电梯初始状态（位置、方向、门状态等）
        ElevatorStatus initialStatus = new ElevatorStatus(
            elevator.getStandbyFloor() * building.getFloorHeight(), // 初始位置
            ElevatorDirection.IDLE, // 初始方向
            0.0, // 初始速度
            false, // 门关闭
            0L, // 门开时间
            new java.util.HashSet<>(), // 没有注册的停靠
            new java.util.ArrayList<>(), // 没有乘客
            0L, // 上次停靠时间
            0, // 正在上车的乘客数
            0 // 正在下车的乘客数
        );
        
        // 更新电梯状态
        elevator = elevator.withStatus(initialStatus);
        
        // 生成初始事件（如空闲事件）
        eventQueue.add(new com.github.charles.works.simelevatortriffic.domain.ElevatorIdleEvent(
            clock.getCurrentTime() + 1000, // 1秒后进入空闲状态
            building.getId(),
            elevator.getId()
        ));
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
        
        // 查找服务该楼层的目的选层电梯组
        ElevatorGroup group = findElevatorGroupForFloor(request.floor());
        if (group == null || group.getControlType() != ElevatorControlType.DESTINATION_CONTROL) {
            return;
        }
        
        // 简化实现：分配第一个可用电梯
        // 实际实现中应该使用更复杂的分配算法
        Elevator assignedElevator = null;
        for (Elevator elevator : group.getElevators()) {
            if (elevator.getStatus().getDirection() == ElevatorDirection.IDLE || 
                elevator.getStatus().getDirection() == request.direction()) {
                assignedElevator = elevator;
                break;
            }
        }
        
        // 如果没有找到合适的电梯，分配第一个电梯
        if (assignedElevator == null && !group.getElevators().isEmpty()) {
            assignedElevator = group.getElevators().get(0);
        }
        
        if (assignedElevator != null) {
            // 在分配的电梯中注册目标楼层
            registerDestinationWithElevator(assignedElevator, request.floor(), request.direction());
            
            // 生成乘客分配结果事件
            eventQueue.add(new com.github.charles.works.simelevatortriffic.domain.PassengerAssignmentEvent(
                clock.getCurrentTime() + 500, // 0.5秒后通知乘客
                building.getId(),
                request.passengerId(),
                assignedElevator.getId(),
                calculateExpectedWaitTime(assignedElevator, request.floor())
            ));
        }
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
        
        // 查找服务该楼层的电梯组
        ElevatorGroup group = findElevatorGroupForFloor(request.floor());
        if (group == null) return;
        
        // 简化实现：寻找第一个服务该楼层且方向匹配的电梯
        // 实际实现中应该使用更复杂的调度算法
        Elevator assignedElevator = null;
        for (Elevator elevator : group.getElevators()) {
            if (elevator.getServiceFloors().contains(request.floor())) {
                ElevatorStatus status = elevator.getStatus();
                // 如果电梯是空闲的或者方向匹配，分配给它
                if (status.getDirection() == ElevatorDirection.IDLE || 
                    status.getDirection() == request.direction() ||
                    status.getRegisteredStops().contains(request.floor())) {
                    assignedElevator = elevator;
                    break;
                }
            }
        }
        
        // 如果没有找到合适的电梯，分配第一个服务该楼层的电梯
        if (assignedElevator == null) {
            for (Elevator elevator : group.getElevators()) {
                if (elevator.getServiceFloors().contains(request.floor())) {
                    assignedElevator = elevator;
                    break;
                }
            }
        }
        
        if (assignedElevator != null) {
            // 在电梯中注册停靠请求
            registerStopWithElevator(assignedElevator, request.floor(), request.direction());
            
            // 生成电梯接近事件
            eventQueue.add(new com.github.charles.works.simelevatortriffic.domain.ElevatorApproachingEvent(
                clock.getCurrentTime() + 1000, // 1秒后接近
                building.getId(),
                assignedElevator.getId(),
                request.floor(),
                request.direction(),
                1.0 // estimated arrival time
            ));
        }
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
        
        // 简化实现：基于配置生成后续事件
        // 在实际实现中，应该根据建筑类型、时间模式和流量分布来生成
        
        // 生成下一个乘客到达事件（简化实现）
        long nextArrivalTime = clock.getCurrentTime() + calculateNextArrivalInterval();
        if (nextArrivalTime < config.getDuration()) {
            PassengerArrivalEvent nextEvent = new PassengerArrivalEvent(
                nextArrivalTime,
                building.getId(),
                event.getFloor(),
                generatePassengersForNextEvent(event.getFloor()),
                event.getTrafficPattern()
            );
            eventQueue.add(nextEvent);
        }
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
    
    public void recordCallRegistration(CallRegistrationEvent event) {
        // 记录召唤注册事件
    }
    
    public void recordPassengerBoarding(PassengerBoardingEvent event) {
        // 记录乘客上车事件
    }
    
    public void recordPassengerAlighting(PassengerAlightingEvent event) {
        // 记录乘客下车事件
    }
    
    public void recordElevatorDirectionChange(ElevatorDirectionChangeEvent event) {
        // 记录电梯方向改变事件
    }
    
    public void recordElevatorInitialization(ElevatorInitializationEvent event) {
        // 记录电梯初始化事件
    }
    
    public void recordTimelineSnapshot(TimelineSnapshot snapshot) {
        // 记录时间线快照
    }
}

class SimulationResultInternal {
    private final String simulationId;
    private final long startTime;
    private final long endTime;
    private final long realDuration;
    private final java.util.List<com.github.charles.works.simelevatortriffic.domain.ElevatorStatistics> elevatorStatistics;
    private final com.github.charles.works.simelevatortriffic.domain.PassengerStatistics passengerStatistics;
    // 结果字段和方法
    
    public SimulationResultInternal() {
        this.simulationId = "";
        this.startTime = 0L;
        this.endTime = 0L;
        this.realDuration = 0L;
        this.elevatorStatistics = new java.util.ArrayList<>();
        this.passengerStatistics = null;
    }
    
    public SimulationResultInternal(String simulationId, long startTime, long endTime, 
                           long realDuration, java.util.List<com.github.charles.works.simelevatortriffic.domain.ElevatorStatistics> elevatorStatistics,
                           com.github.charles.works.simelevatortriffic.domain.PassengerStatistics passengerStatistics) {
        this.simulationId = simulationId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.realDuration = realDuration;
        this.elevatorStatistics = elevatorStatistics;
        this.passengerStatistics = passengerStatistics;
    }
    
    public SimulationResultInternal(String simulationId, long startTime, long endTime) {
        this.simulationId = simulationId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.realDuration = 0L;
        this.elevatorStatistics = new java.util.ArrayList<>();
        this.passengerStatistics = null;
    }
    
    // Getter方法
    public String getSimulationId() { return simulationId; }
    public long getStartTime() { return startTime; }
    public long getEndTime() { return endTime; }
    public long getDuration() { return endTime - startTime; }
    public long getRealDuration() { return realDuration; }
    public java.util.List<com.github.charles.works.simelevatortriffic.domain.ElevatorStatistics> getElevatorStatistics() { return elevatorStatistics; }
    public com.github.charles.works.simelevatortriffic.domain.PassengerStatistics getPassengerStatistics() { return passengerStatistics; }
}