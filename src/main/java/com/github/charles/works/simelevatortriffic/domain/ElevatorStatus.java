package com.github.charles.works.simelevatortriffic.domain;

/**
 * 电梯状态，实时跟踪电梯运行情况
 * 根据PDF中"EMS记录数据从每次电梯停靠包括时间戳"的描述设计
 */
public class ElevatorStatus {
    private final double position;          // 位置(米)
    private final ElevatorDirection direction;
    private final double speed;
    private final boolean doorOpen;
    private final long doorOpenTime;        // 开门已持续时间(毫秒)
    private final java.util.Set<Integer> registeredStops;
    private final java.util.List<Passenger> passengers;
    private final long lastStopTime;        // 上次停靠时间(仿真时间戳)
    private final int boardingPassengers;   // 正在上车的乘客数
    private final int alightingPassengers;  // 正在下车的乘客数
    
    public ElevatorStatus(double position, ElevatorDirection direction, double speed,
                         boolean doorOpen, long doorOpenTime, java.util.Set<Integer> registeredStops,
                         java.util.List<Passenger> passengers, long lastStopTime, 
                         int boardingPassengers, int alightingPassengers) {
        this.position = position;
        this.direction = direction;
        this.speed = speed;
        this.doorOpen = doorOpen;
        this.doorOpenTime = doorOpenTime;
        this.registeredStops = new java.util.HashSet<>(registeredStops);
        this.passengers = new java.util.ArrayList<>(passengers);
        this.lastStopTime = lastStopTime;
        this.boardingPassengers = boardingPassengers;
        this.alightingPassengers = alightingPassengers;
    }
    
    // Getter方法
    public double getPosition() { return position; }
    public ElevatorDirection getDirection() { return direction; }
    public double getSpeed() { return speed; }
    public boolean isDoorOpen() { return doorOpen; }
    public long getDoorOpenTime() { return doorOpenTime; }
    public java.util.Set<Integer> getRegisteredStops() { return new java.util.HashSet<>(registeredStops); }
    public java.util.List<Passenger> getPassengers() { return new java.util.ArrayList<>(passengers); }
    public long getLastStopTime() { return lastStopTime; }
    public int getBoardingPassengers() { return boardingPassengers; }
    public int getAlightingPassengers() { return alightingPassengers; }
    
    public ElevatorStatus withDoorOpen(boolean doorOpen) {
        return new ElevatorStatus(position, direction, speed, doorOpen, doorOpenTime,
                                 registeredStops, passengers, lastStopTime, 
                                 boardingPassengers, alightingPassengers);
    }
}