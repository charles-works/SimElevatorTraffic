package com.github.charles.works.simelevatortriffic.domain;

import java.util.List;

/**
 * 时间线快照，记录特定时间点的仿真状态
 */
public class TimelineSnapshot {
    private final long timestamp;
    private final List<ElevatorStatusSnapshot> elevatorStates;
    private final PassengerDistribution passengerDistribution;
    
    public TimelineSnapshot(long timestamp, List<ElevatorStatusSnapshot> elevatorStates, 
                           PassengerDistribution passengerDistribution) {
        this.timestamp = timestamp;
        this.elevatorStates = elevatorStates;
        this.passengerDistribution = passengerDistribution;
    }
    
    // Getter方法
    public long getTimestamp() { return timestamp; }
    public List<ElevatorStatusSnapshot> getElevatorStates() { return elevatorStates; }
    public PassengerDistribution getPassengerDistribution() { return passengerDistribution; }
}