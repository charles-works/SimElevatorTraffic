package com.github.charles.works.simelevatortriffic.domain;

/**
 * 电梯停靠完成事件
 */
public final class ElevatorStopCompletionEvent extends SimulationEvent {
    private final String elevatorId;
    private final int floor;
    private final long stopStartTime;
    
    public ElevatorStopCompletionEvent(long timestamp, String buildingId, String elevatorId, 
                                      int floor, long stopStartTime) {
        super(timestamp, buildingId);
        this.elevatorId = elevatorId;
        this.floor = floor;
        this.stopStartTime = stopStartTime;
    }
    
    public String getElevatorId() {
        return elevatorId;
    }
    
    public int getFloor() {
        return floor;
    }
    
    public long getStopStartTime() {
        return stopStartTime;
    }
}