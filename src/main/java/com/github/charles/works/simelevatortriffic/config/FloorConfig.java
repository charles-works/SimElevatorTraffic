package com.github.charles.works.simelevatortriffic.config;

import com.github.charles.works.simelevatortriffic.domain.FloorUsage;

/**
 * 楼层配置类，支持每层不同的用途、人数等配置
 */
public class FloorConfig {
    private int floorNumber;
    private FloorUsage usage;
    private int population;
    private double arrivalRate;
    private double floorHeight;
    
    // 构造函数
    public FloorConfig() {}
    
    public FloorConfig(int floorNumber, FloorUsage usage, int population, 
                      double arrivalRate, double floorHeight) {
        this.floorNumber = floorNumber;
        this.usage = usage;
        this.population = population;
        this.arrivalRate = arrivalRate;
        this.floorHeight = floorHeight;
    }
    
    // Getter和Setter方法
    public int getFloorNumber() { return floorNumber; }
    public void setFloorNumber(int floorNumber) { this.floorNumber = floorNumber; }
    
    public FloorUsage getUsage() { return usage; }
    public void setUsage(FloorUsage usage) { this.usage = usage; }
    
    public int getPopulation() { return population; }
    public void setPopulation(int population) { this.population = population; }
    
    public double getArrivalRate() { return arrivalRate; }
    public void setArrivalRate(double arrivalRate) { this.arrivalRate = arrivalRate; }
    
    public double getFloorHeight() { return floorHeight; }
    public void setFloorHeight(double floorHeight) { this.floorHeight = floorHeight; }
}