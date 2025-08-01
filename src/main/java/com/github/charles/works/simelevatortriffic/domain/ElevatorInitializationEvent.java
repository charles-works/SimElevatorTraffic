package com.github.charles.works.simelevatortriffic.domain;

/**
 * 电梯初始化事件
 */
public final class ElevatorInitializationEvent extends SimulationEvent {
    private final String elevatorId;
    
    public ElevatorInitializationEvent(long timestamp, String buildingId, String elevatorId) {
        super(timestamp, buildingId);
        this.elevatorId = elevatorId;
    }
    
    public String getElevatorId() {
        return elevatorId;
    }
}