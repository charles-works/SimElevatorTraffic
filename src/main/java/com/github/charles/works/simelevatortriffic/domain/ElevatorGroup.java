package com.github.charles.works.simelevatortriffic.domain;

import java.util.*;

/**
 * 电梯组，包含控制类型和组内电梯列表
 * 实现PDF中描述的单控、并联、群控、目的选层四种控制方式
 */
public class ElevatorGroup {
    private final String id;
    private final ElevatorControlType controlType;
    private final List<Elevator> elevators;
    private final List<Integer> servedFloors;
    private TrafficPattern currentTrafficPattern;
    
    public ElevatorGroup(String id, ElevatorControlType controlType, 
                        List<Elevator> elevators, List<Integer> servedFloors,
                        TrafficPattern currentTrafficPattern) {
        this.id = id;
        this.controlType = controlType;
        this.elevators = new ArrayList<>(elevators);
        this.servedFloors = new ArrayList<>(servedFloors);
        this.currentTrafficPattern = currentTrafficPattern;
    }
    
    // Getter方法
    public String getId() { return id; }
    public ElevatorControlType getControlType() { return controlType; }
    public List<Elevator> getElevators() { return new ArrayList<>(elevators); }
    public List<Integer> getServedFloors() { return new ArrayList<>(servedFloors); }
    public TrafficPattern getCurrentTrafficPattern() { return currentTrafficPattern; }
    
    // Setter方法
    public void setCurrentTrafficPattern(TrafficPattern currentTrafficPattern) {
        this.currentTrafficPattern = currentTrafficPattern;
    }
}