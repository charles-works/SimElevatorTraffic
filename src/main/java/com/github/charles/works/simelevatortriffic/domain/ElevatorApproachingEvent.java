package com.github.charles.works.simelevatortriffic.domain;

/**
 * 电梯接近事件
 */
public class ElevatorApproachingEvent extends SimulationEvent {
    private final String elevatorId;
    private final int floor;
    private final ElevatorDirection direction;
    private final double estimatedArrivalTime;
    
    public ElevatorApproachingEvent(long timestamp, String buildingId, String elevatorId, 
                                   int floor, ElevatorDirection direction, double estimatedArrivalTime) {
        super(timestamp, buildingId);
        this.elevatorId = elevatorId;
        this.floor = floor;
        this.direction = direction;
        this.estimatedArrivalTime = estimatedArrivalTime;
    }
    
    // Getter方法
    public String getElevatorId() { return elevatorId; }
    public int getFloor() { return floor; }
    public ElevatorDirection getDirection() { return direction; }
    public double getEstimatedArrivalTime() { return estimatedArrivalTime; }
    
    @Override
    public String toString() {
        return "ElevatorApproachingEvent{" +
                "timestamp=" + getTimestamp() +
                ", buildingId='" + getBuildingId() + '\'' +
                ", elevatorId='" + elevatorId + '\'' +
                ", floor=" + floor +
                ", direction=" + direction +
                ", estimatedArrivalTime=" + estimatedArrivalTime +
                '}';
    }
}