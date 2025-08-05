package com.github.charles.works.simelevatortriffic.domain;

/**
 * 乘客到达事件
 * 对应PDF中描述的"乘客到达电梯厅注册接送请求"
 */
public final class PassengerArrivalEvent extends SimulationEvent {
    private final int floor;
    private final java.util.List<Passenger> passengers;
    private final TrafficPattern trafficPattern;
    
    public PassengerArrivalEvent(long timestamp, String buildingId, int floor, java.util.List<Passenger> passengers) {
        super(timestamp, buildingId);
        this.floor = floor;
        this.passengers = passengers;
        this.trafficPattern = null;
    }
    
    public PassengerArrivalEvent(long timestamp, String buildingId, int floor, java.util.List<Passenger> passengers, TrafficPattern trafficPattern) {
        super(timestamp, buildingId);
        this.floor = floor;
        this.passengers = passengers;
        this.trafficPattern = trafficPattern;
    }
    
    public int getFloor() {
        return floor;
    }
    
    public java.util.List<Passenger> getPassengers() {
        return passengers;
    }
    
    public TrafficPattern getTrafficPattern() {
        return trafficPattern;
    }
}