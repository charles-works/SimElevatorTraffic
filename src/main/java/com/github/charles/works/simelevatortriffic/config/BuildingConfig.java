package com.github.charles.works.simelevatortriffic.config;

import com.github.charles.works.simelevatortriffic.domain.BuildingType;
import java.util.List;

/**
 * 建筑配置类
 */
public class BuildingConfig {
    private String id;
    private String name;
    private int floors;
    private double floorHeight; // 默认层高
    private BuildingType type;
    private List<FloorConfig> floorConfigs; // 各层详细配置
    
    // 构造函数
    public BuildingConfig() {}
    
    // Getter和Setter方法
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getFloors() { return floors; }
    public void setFloors(int floors) { this.floors = floors; }
    
    public double getFloorHeight() { return floorHeight; }
    public void setFloorHeight(double floorHeight) { this.floorHeight = floorHeight; }
    
    public BuildingType getType() { return type; }
    public void setType(BuildingType type) { this.type = type; }
    
    public List<FloorConfig> getFloorConfigs() { return floorConfigs; }
    public void setFloorConfigs(List<FloorConfig> floorConfigs) { this.floorConfigs = floorConfigs; }
}