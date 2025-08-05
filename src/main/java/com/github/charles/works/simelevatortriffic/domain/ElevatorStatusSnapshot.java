package com.github.charles.works.simelevatortriffic.domain;

/**
 * 电梯状态快照，记录电梯在特定时间点的状态
 */
public class ElevatorStatusSnapshot {
    private final String elevatorId;
    private final double position;
    private final ElevatorDirection direction;
    private final double speed;
    private final boolean doorOpen;
    private final int passengerCount;
    
    public ElevatorStatusSnapshot(String elevatorId, double position, ElevatorDirection direction,
                                 double speed, boolean doorOpen, int passengerCount) {
        this.elevatorId = elevatorId;
        this.position = position;
        this.direction = direction;
        this.speed = speed;
        this.doorOpen = doorOpen;
        this.passengerCount = passengerCount;
    }
    
    // Getter方法
    public String getElevatorId() { return elevatorId; }
    public double getPosition() { return position; }
    public ElevatorDirection getDirection() { return direction; }
    public double getSpeed() { return speed; }
    public boolean isDoorOpen() { return doorOpen; }
    public int getPassengerCount() { return passengerCount; }
}