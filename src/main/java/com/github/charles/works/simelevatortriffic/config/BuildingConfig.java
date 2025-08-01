package com.github.charles.works.simelevatortriffic.config;

/**
 * 建筑配置类
 */
public class BuildingConfig {
    private String name;
    private int totalFloors;
    private double floorHeight;
    private String buildingType;
    
    // 构造函数
    public BuildingConfig() {}
    
    // Getter和Setter方法
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getTotalFloors() { return totalFloors; }
    public void setTotalFloors(int totalFloors) { this.totalFloors = totalFloors; }
    
    public double getFloorHeight() { return floorHeight; }
    public void setFloorHeight(double floorHeight) { this.floorHeight = floorHeight; }
    
    public String getBuildingType() { return buildingType; }
    public void setBuildingType(String buildingType) { this.buildingType = buildingType; }
}