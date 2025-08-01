package com.github.charles.works.simelevatortriffic.domain;

/**
 * 电梯方向改变事件
 */
public final class ElevatorDirectionChangeEvent extends SimulationEvent {
    private final String elevatorId;
    private final ElevatorDirection newDirection;
    
    public ElevatorDirectionChangeEvent(long timestamp, String buildingId, String elevatorId, 
                                       ElevatorDirection newDirection) {
        super(timestamp, buildingId);
        this.elevatorId = elevatorId;
        this.newDirection = newDirection;
    }
    
    public String getElevatorId() {
        return elevatorId;
    }
    
    public ElevatorDirection getNewDirection() {
        return newDirection;
    }
}