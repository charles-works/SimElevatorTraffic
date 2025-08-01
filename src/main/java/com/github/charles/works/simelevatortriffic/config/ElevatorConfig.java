package com.github.charles.works.simelevatortriffic.config;

/**
 * 电梯配置类
 */
public class ElevatorConfig {
    private int capacity;
    private double ratedSpeed;
    private double acceleration;
    private double deceleration;
    private double doorWidth;
    private int standbyFloor;
    private java.util.List<Integer> serviceFloors;
    
    // 构造函数
    public ElevatorConfig() {}
    
    // Getter和Setter方法
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    
    public double getRatedSpeed() { return ratedSpeed; }
    public void setRatedSpeed(double ratedSpeed) { this.ratedSpeed = ratedSpeed; }
    
    public double getAcceleration() { return acceleration; }
    public void setAcceleration(double acceleration) { this.acceleration = acceleration; }
    
    public double getDeceleration() { return deceleration; }
    public void setDeceleration(double deceleration) { this.deceleration = deceleration; }
    
    public double getDoorWidth() { return doorWidth; }
    public void setDoorWidth(double doorWidth) { this.doorWidth = doorWidth; }
    
    public int getStandbyFloor() { return standbyFloor; }
    public void setStandbyFloor(int standbyFloor) { this.standbyFloor = standbyFloor; }
    
    public java.util.List<Integer> getServiceFloors() { return serviceFloors; }
    public void setServiceFloors(java.util.List<Integer> serviceFloors) { this.serviceFloors = serviceFloors; }
}