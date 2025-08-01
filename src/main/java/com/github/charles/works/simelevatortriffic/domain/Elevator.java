package com.github.charles.works.simelevatortriffic.domain;

import java.util.*;

/**
 * 电梯对象，包含所有物理和运行参数
 * 参数设置基于PDF中描述的电梯动力学准确模型
 */
public class Elevator {
    private final String id;
    private final int capacity;             // 载客量(人)
    private final double ratedSpeed;        // 额定速度(m/s)
    private final double acceleration;      // 加速度(m/s²)
    private final double deceleration;      // 减速度(m/s²)
    private final double doorWidth;         // 门宽(m)
    private final int standbyFloor;         // 基准层
    private final Set<Integer> serviceFloors; // 服务楼层
    private ElevatorStatus status;          // 当前状态
    
    public Elevator(String id, int capacity, double ratedSpeed, double acceleration, 
                   double deceleration, double doorWidth, int standbyFloor, 
                   Set<Integer> serviceFloors, ElevatorStatus status) {
        this.id = id;
        this.capacity = capacity;
        this.ratedSpeed = ratedSpeed;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.doorWidth = doorWidth;
        this.standbyFloor = standbyFloor;
        this.serviceFloors = new HashSet<>(serviceFloors);
        this.status = status;
    }
    
    // Getter方法
    public String getId() { return id; }
    public int getCapacity() { return capacity; }
    public double getRatedSpeed() { return ratedSpeed; }
    public double getAcceleration() { return acceleration; }
    public double getDeceleration() { return deceleration; }
    public double getDoorWidth() { return doorWidth; }
    public int getStandbyFloor() { return standbyFloor; }
    public Set<Integer> getServiceFloors() { return new HashSet<>(serviceFloors); }
    public ElevatorStatus getStatus() { return status; }
    
    // Setter方法
    public void setStatus(ElevatorStatus status) {
        this.status = status;
    }
    
    public Elevator withStatus(ElevatorStatus newStatus) {
        return new Elevator(id, capacity, ratedSpeed, acceleration, deceleration, 
                           doorWidth, standbyFloor, serviceFloors, newStatus);
    }
}