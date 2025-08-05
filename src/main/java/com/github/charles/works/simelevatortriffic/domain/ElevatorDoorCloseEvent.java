package com.github.charles.works.simelevatortriffic.domain;

/**
 * 电梯门关闭事件
 */
public class ElevatorDoorCloseEvent extends SimulationEvent {
    private final String elevatorId;
    private final int floor;
    
    public ElevatorDoorCloseEvent(long timestamp, String buildingId, String elevatorId, int floor) {
        super(timestamp, buildingId);
        this.elevatorId = elevatorId;
        this.floor = floor;
    }
    
    // Getter方法
    public String getElevatorId() { return elevatorId; }
    public int getFloor() { return floor; }
    
    @Override
    public String toString() {
        return "ElevatorDoorCloseEvent{" +
                "timestamp=" + getTimestamp() +
                ", buildingId='" + getBuildingId() + '\'' +
                ", elevatorId='" + elevatorId + '\'' +
                ", floor=" + floor +
                '}';
    }
}