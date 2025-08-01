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
        // TODO: 实现具体的电梯召唤处理逻辑
        return new com.github.charles.works.simelevatortriffic.service.ServiceHelpers.AssignmentResult("default", 0.0);
    }
    
    /**
     * 更新电梯状态
     */
    public ElevatorStatus updateElevatorStatus(Elevator elevator, long currentTime) {
        return elevatorControlService.updateElevatorStatus(elevator, currentTime);
    }
}