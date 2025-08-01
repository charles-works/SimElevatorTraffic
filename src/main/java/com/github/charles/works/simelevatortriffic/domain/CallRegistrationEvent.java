package com.github.charles.works.simelevatortriffic.domain;

/**
 * 召唤注册事件
 */
public final class CallRegistrationEvent extends SimulationEvent {
    private final int floor;
    private final ElevatorDirection direction;
    private final String passengerId;
    
    public CallRegistrationEvent(long timestamp, String buildingId, int floor, 
                                ElevatorDirection direction, String passengerId) {
        super(timestamp, buildingId);
        this.floor = floor;
        this.direction = direction;
        this.passengerId = passengerId;
    }
    
    public int getFloor() {
        return floor;
    }
    
    public ElevatorDirection getDirection() {
        return direction;
    }
    
    public String getPassengerId() {
        return passengerId;
    }
}