package com.github.charles.works.simelevatortriffic.config;

import com.github.charles.works.simelevatortriffic.domain.ElevatorControlType;
import java.util.List;

/**
 * 电梯组配置类
 */
public class ElevatorGroupConfig {
    private String id;
    private ElevatorControlType controlType;
    private List<ElevatorConfig> elevators;
    private List<Integer> servedFloors;
    
    // 构造函数
    public ElevatorGroupConfig() {}
    
    // Getter和Setter方法
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public ElevatorControlType getControlType() { return controlType; }
    public void setControlType(ElevatorControlType controlType) { this.controlType = controlType; }
    
    public List<ElevatorConfig> getElevators() { return elevators; }
    public void setElevators(List<ElevatorConfig> elevators) { this.elevators = elevators; }
    
    public List<Integer> getServedFloors() { return servedFloors; }
    public void setServedFloors(List<Integer> servedFloors) { this.servedFloors = servedFloors; }
}