package com.github.charles.works.simelevatortriffic.service;

import com.github.charles.works.simelevatortriffic.domain.*;

import java.util.List;

/**
 * 电梯服务类，协调电梯控制、乘客流和OD估计服务
 */
public class ElevatorService {
    private final ODEstimationService odEstimationService;
    private final PassengerFlowService passengerFlowService;
    private final ElevatorControlService elevatorControlService;
    
    public ElevatorService(ODEstimationService odEstimationService, 
                          PassengerFlowService passengerFlowService,
                          ElevatorControlService elevatorControlService) {
        this.odEstimationService = odEstimationService;
        this.passengerFlowService = passengerFlowService;
        this.elevatorControlService = elevatorControlService;
    }
    
    /**
     * 估算OD矩阵
     */
    public ODMetricMatrix estimateODMatrix(Building building, List<BatchArrivalProfile> profiles) {
        return odEstimationService.estimateODMatrix(building, profiles);
    }
    
    /**
     * 生成乘客流
     */
    public List<PassengerArrivalEvent> generatePassengerFlow(Building building, long startTime, long duration) {
        return passengerFlowService.generatePassengerFlow(building, startTime, duration);
    }
    
    /**
     * 处理电梯召唤请求
     */
    public com.github.charles.works.simelevatortriffic.service.ServiceHelpers.AssignmentResult handleCallRequest(ElevatorGroup group, com.github.charles.works.simelevatortriffic.service.ServiceHelpers.CallRequest request) {
        // 查找最适合的电梯
        Elevator bestElevator = null;
        double minWaitTime = Double.MAX_VALUE;
        
        // 遍历电梯组中的所有电梯
        for (Elevator elevator : group.getElevators()) {
            // 计算该电梯的预期等待时间
            double expectedWaitTime = calculateExpectedWaitTime(elevator, request.getFloor(), request.getDirection());
            
            // 选择等待时间最短的电梯
            if (expectedWaitTime < minWaitTime) {
                minWaitTime = expectedWaitTime;
                bestElevator = elevator;
            }
        }
        
        // 如果找到了合适的电梯，则分配给它
        if (bestElevator != null) {
            // 向电梯添加召唤请求
            bestElevator.addCallRequest(request.getFloor(), request.getDirection());
            return new com.github.charles.works.simelevatortriffic.service.ServiceHelpers.AssignmentResult(
                bestElevator.getId(), minWaitTime);
        }
        
        // 如果没有找到合适的电梯，返回默认结果
        return new com.github.charles.works.simelevatortriffic.service.ServiceHelpers.AssignmentResult("default", Double.MAX_VALUE);
    }
    
    /**
     * 计算电梯到指定楼层的预期等待时间
     */
    private double calculateExpectedWaitTime(Elevator elevator, int floor, ElevatorDirection direction) {
        // 简化的等待时间计算逻辑
        // 实际实现可能需要考虑电梯当前状态、位置、速度、停靠计划等因素
        
        int currentFloor = elevator.getCurrentFloor();
        ElevatorDirection currentDirection = elevator.getDirection();
        
        // 如果电梯空闲，等待时间相对简单
        if (currentDirection == ElevatorDirection.IDLE) {
            return Math.abs(floor - currentFloor) * elevator.getFloorTravelTime();
        }
        
        // 如果电梯正在运行且方向一致
        if ((currentDirection == ElevatorDirection.UP && direction == ElevatorDirection.UP && floor >= currentFloor) ||
            (currentDirection == ElevatorDirection.DOWN && direction == ElevatorDirection.DOWN && floor <= currentFloor)) {
            // 电梯会在经过目标楼层时停靠
            return Math.abs(floor - currentFloor) * elevator.getFloorTravelTime();
        }
        
        // 如果电梯正在运行但方向不一致，需要先完成当前任务再返回
        // 这里简化处理，实际应考虑电梯的完整行程计划
        // 由于我们没有Building引用，我们使用简化的值
        int highestFloor = 20; // 假设建筑最高20层
        int lowestFloor = 1;
        
        if (currentDirection == ElevatorDirection.UP) {
            // 电梯向上运行，先到最高层再向下
            return (Math.abs(highestFloor - currentFloor) + Math.abs(highestFloor - floor)) * elevator.getFloorTravelTime();
        } else {
            // 电梯向下运行，先到最底层再向上
            return (Math.abs(currentFloor - lowestFloor) + Math.abs(floor - lowestFloor)) * elevator.getFloorTravelTime();
        }
    }
    
    /**
     * 更新电梯状态
     */
    public ElevatorStatus updateElevatorStatus(Elevator elevator, long currentTime) {
        return elevatorControlService.updateElevatorStatus(elevator, currentTime);
    }
}