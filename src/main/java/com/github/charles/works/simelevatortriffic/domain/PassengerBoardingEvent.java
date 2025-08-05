package com.github.charles.works.simelevatortriffic.domain;

import java.util.List;
import java.util.ArrayList;

/**
 * 乘客上车事件
 */
public final class PassengerBoardingEvent extends SimulationEvent {
    private final String elevatorId;
    private final String passengerId;
    private final int floor;
    private final List<Passenger> passengers;
    
    public PassengerBoardingEvent(long timestamp, String buildingId, String elevatorId, 
                                 String passengerId, int floor) {
        super(timestamp, buildingId);
        this.elevatorId = elevatorId;
        this.passengerId = passengerId;
        this.floor = floor;
        this.passengers = new ArrayList<>();
    }
    
    public PassengerBoardingEvent(long timestamp, String buildingId, String elevatorId, 
                                 List<Passenger> passengers, int floor) {
        super(timestamp, buildingId);
        this.elevatorId = elevatorId;
        this.passengers = passengers;
        this.floor = floor;
        this.passengerId = passengers.isEmpty() ? null : passengers.get(0).getId();
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
    
    public List<Passenger> getPassengers() {
        return passengers;
    }
}