package com.github.charles.works.simelevatortriffic.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 乘客下车事件
 */
public final class PassengerAlightingEvent extends SimulationEvent {
    private final String elevatorId;
    private final String passengerId;
    private final int floor;
    private final List<String> passengerIds;
    
    public PassengerAlightingEvent(long timestamp, String buildingId, String elevatorId, 
                                  String passengerId, int floor) {
        super(timestamp, buildingId);
        this.elevatorId = elevatorId;
        this.passengerId = passengerId;
        this.floor = floor;
        this.passengerIds = Collections.singletonList(passengerId);
    }
    
    public PassengerAlightingEvent(long timestamp, String buildingId, String elevatorId, 
                                  List<String> passengerIds, int floor) {
        super(timestamp, buildingId);
        this.elevatorId = elevatorId;
        this.passengerIds = passengerIds;
        this.floor = floor;
        this.passengerId = passengerIds.isEmpty() ? null : passengerIds.get(0);
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
    
    public List<String> getPassengerIds() {
        return passengerIds;
    }
}