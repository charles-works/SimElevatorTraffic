package com.github.charles.works.simelevatortriffic.domain;

/**
 * 电梯停靠事件
 */
public final class ElevatorStopEvent extends SimulationEvent {
    private final String elevatorId;
    private final int floor;
    private final int boardingCount;
    private final int alightingCount;
    
    public ElevatorStopEvent(long timestamp, String buildingId, String elevatorId, 
                            int floor, int boardingCount, int alightingCount) {
        super(timestamp, buildingId);
        this.elevatorId = elevatorId;
        this.floor = floor;
        this.boardingCount = boardingCount;
        this.alightingCount = alightingCount;
    }
    
    public String getElevatorId() {
        return elevatorId;
    }
    
    public int getFloor() {
        return floor;
    }
    
    public int getBoardingCount() {
        return boardingCount;
    }
    
    public int getAlightingCount() {
        return alightingCount;
    }
}