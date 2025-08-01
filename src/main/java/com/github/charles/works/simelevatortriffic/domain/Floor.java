package com.github.charles.works.simelevatortriffic.domain;

import java.util.*;

/**
 * 楼层对象，包含该楼层的用途、人数等特性
 * PDF研究表明，不同用途楼层的交通特性显著不同
 */
public class Floor {
    private final int floorNumber;
    private final FloorUsage usage;    // 楼层用途(大厅、办公、商业等)
    private final int population;      // 楼层人数
    private final double arrivalRate;  // 基础到达率(人/分钟)
    private final Map<TimePeriod, PopulationDistribution> populationByTime;
    
    public Floor(int floorNumber, FloorUsage usage, int population, 
                double arrivalRate, Map<TimePeriod, PopulationDistribution> populationByTime) {
        this.floorNumber = floorNumber;
        this.usage = usage;
        this.population = population;
        this.arrivalRate = arrivalRate;
        this.populationByTime = new HashMap<>(populationByTime);
    }
    
    // Getter方法
    public int getFloorNumber() { return floorNumber; }
    public FloorUsage getUsage() { return usage; }
    public int getPopulation() { return population; }
    public double getArrivalRate() { return arrivalRate; }
    public Map<TimePeriod, PopulationDistribution> getPopulationByTime() { 
        return new HashMap<>(populationByTime); 
    }
    
    public PopulationDistribution getPopulationDistribution(TimePeriod period) {
        return populationByTime.get(period);
    }
}