package com.github.charles.works.simelevatortriffic.domain;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 建筑对象，包含所有楼层和电梯组信息
 * 根据PDF研究，建筑特性直接影响交通模式和乘客行为
 */
public class Building {
    private final String id;
    private final String name;
    private final int totalFloors;
    private final double floorHeight; // 层高(米)
    private final BuildingType type;  // 建筑类型(办公楼、商场等)
    private final List<Floor> floors;
    private final List<ElevatorGroup> elevatorGroups;
    
    public Building(String id, String name, int totalFloors, double floorHeight, 
                   BuildingType type, List<Floor> floors, List<ElevatorGroup> elevatorGroups) {
        this.id = id;
        this.name = name;
        this.totalFloors = totalFloors;
        this.floorHeight = floorHeight;
        this.type = type;
        this.floors = new ArrayList<>(floors);
        this.elevatorGroups = new ArrayList<>(elevatorGroups);
    }
    
    // Getter方法
    public String getId() { return id; }
    public String getName() { return name; }
    public int getTotalFloors() { return totalFloors; }
    public double getFloorHeight() { return floorHeight; }
    public BuildingType getType() { return type; }
    public List<Floor> getFloors() { return new ArrayList<>(floors); }
    public List<ElevatorGroup> getElevatorGroups() { return new ArrayList<>(elevatorGroups); }
    
    public Floor getFloor(int floorNumber) {
        return floors.stream()
                .filter(f -> f.getFloorNumber() == floorNumber)
                .findFirst()
                .orElse(null);
    }
    
    public int getTotalPopulation() {
        return floors.stream()
                .mapToInt(Floor::getPopulation)
                .sum();
    }
}