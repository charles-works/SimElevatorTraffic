package com.github.charles.works.simelevatortriffic.api;

/**
 * 仿真请求类
 */
public class SimulationRequest {
    private com.github.charles.works.simelevatortriffic.config.BuildingConfig buildingConfig;
    private java.util.List<com.github.charles.works.simelevatortriffic.config.ElevatorGroupConfig> elevatorGroups;
    private com.github.charles.works.simelevatortriffic.config.SimulationConfig simulationConfig;
    
    // 构造函数、getter和setter方法
    public SimulationRequest() {}
    
    public com.github.charles.works.simelevatortriffic.config.BuildingConfig getBuildingConfig() { return buildingConfig; }
    public void setBuildingConfig(com.github.charles.works.simelevatortriffic.config.BuildingConfig buildingConfig) { this.buildingConfig = buildingConfig; }
    
    public java.util.List<com.github.charles.works.simelevatortriffic.config.ElevatorGroupConfig> getElevatorGroups() { return elevatorGroups; }
    public void setElevatorGroups(java.util.List<com.github.charles.works.simelevatortriffic.config.ElevatorGroupConfig> elevatorGroups) { this.elevatorGroups = elevatorGroups; }
    
    public com.github.charles.works.simelevatortriffic.config.SimulationConfig getSimulationConfig() { return simulationConfig; }
    public void setSimulationConfig(com.github.charles.works.simelevatortriffic.config.SimulationConfig simulationConfig) { this.simulationConfig = simulationConfig; }
}