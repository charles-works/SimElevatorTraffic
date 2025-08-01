package com.github.charles.works.simelevatortriffic.config;

/**
 * 电梯组配置类
 */
public class ElevatorGroupConfig {
    private String controlType;
    private java.util.List<ElevatorConfig> elevators;
    
    // 构造函数
    public ElevatorGroupConfig() {}
    
    // Getter和Setter方法
    public String getControlType() { return controlType; }
    public void setControlType(String controlType) { this.controlType = controlType; }
    
    public java.util.List<ElevatorConfig> getElevators() { return elevators; }
    public void setElevators(java.util.List<ElevatorConfig> elevators) { this.elevators = elevators; }
}