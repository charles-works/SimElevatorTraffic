package com.github.charles.works.simelevatortriffic.domain;

/**
 * 乘客分配事件
 */
public class PassengerAssignmentEvent extends SimulationEvent {
    private final String passengerId;
    private final String assignedElevatorId;
    private final double expectedWaitTime;
    
    public PassengerAssignmentEvent(long timestamp, String buildingId, String passengerId, 
                                   String assignedElevatorId, double expectedWaitTime) {
        super(timestamp, buildingId);
        this.passengerId = passengerId;
        this.assignedElevatorId = assignedElevatorId;
        this.expectedWaitTime = expectedWaitTime;
    }
    
    // Getter方法
    public String getPassengerId() { return passengerId; }
    public String getAssignedElevatorId() { return assignedElevatorId; }
    public double getExpectedWaitTime() { return expectedWaitTime; }
    
    @Override
    public String toString() {
        return "PassengerAssignmentEvent{" +
                "timestamp=" + getTimestamp() +
                ", buildingId='" + getBuildingId() + '\'' +
                ", passengerId='" + passengerId + '\'' +
                ", assignedElevatorId='" + assignedElevatorId + '\'' +
                ", expectedWaitTime=" + expectedWaitTime +
                '}';
    }
}