package com.github.charles.works.simelevatortriffic.domain;

/**
 * 电梯空闲事件
 */
public class ElevatorIdleEvent extends SimulationEvent {
    private final String elevatorId;
    
    public ElevatorIdleEvent(long timestamp, String buildingId, String elevatorId) {
        super(timestamp, buildingId);
        this.elevatorId = elevatorId;
    }
    
    // Getter方法
    public String getElevatorId() { return elevatorId; }
    
    @Override
    public String toString() {
        return "ElevatorIdleEvent{" +
                "timestamp=" + getTimestamp() +
                ", buildingId='" + getBuildingId() + '\'' +
                ", elevatorId='" + elevatorId + '\'' +
                '}';
    }
}