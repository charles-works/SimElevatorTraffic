package com.github.charles.works.simelevatortriffic.domain;

/**
 * 乘客下车事件
 */
public final class PassengerAlightingEvent extends SimulationEvent {
    private final String elevatorId;
    private final String passengerId;
    private final int floor;
    
    public PassengerAlightingEvent(long timestamp, String buildingId, String elevatorId, 
                                  String passengerId, int floor) {
        super(timestamp, buildingId);
        this.elevatorId = elevatorId;
        this.passengerId = passengerId;
        this.floor = floor;
    }
    
    public String getElevatorId() {
        return elevatorId;
    }
    
    public String getPassengerId() {
        return passengerId;
    }
    
    public int getFloor() {
        return floor;
    }
}