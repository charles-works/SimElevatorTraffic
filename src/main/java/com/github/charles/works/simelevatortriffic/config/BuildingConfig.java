package com.github.charles.works.simelevatortriffic.config;

import com.github.charles.works.simelevatortriffic.domain.BuildingType;

/**
 * 建筑配置类
 */
public class BuildingConfig {
    private String id;
    private String name;
    private int totalFloors;
    private double floorHeight;
    private BuildingType type;
    
    // 构造函数
    public BuildingConfig() {}
    
    // Getter和Setter方法
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getFloors() { return totalFloors; }
    public void setFloors(int totalFloors) { this.totalFloors = totalFloors; }
    
    public double getFloorHeight() { return floorHeight; }
    public void setFloorHeight(double floorHeight) { this.floorHeight = floorHeight; }
    
    public BuildingType getType() { return type; }
    public void setType(BuildingType type) { this.type = type; }
}