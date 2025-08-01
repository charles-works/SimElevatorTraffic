package com.github.charles.works.simelevatortriffic.domain;

import java.util.*;

/**
 * 人口分布，按时间段定义楼层人数和交通特性
 * 实现PDF中描述的不同时段(早高峰、午餐高峰等)特性
 */
public class PopulationDistribution {
    private final int population;
    private final double incomingTrafficRatio;   // 进楼交通比例
    private final double outgoingTrafficRatio;   // 出楼交通比例
    private final double interfloorTrafficRatio; // 楼层间交通比例
    private final BatchArrivalProfile batchProfile; // 批次到达特性
    
    public PopulationDistribution(int population, double incomingTrafficRatio, 
                                 double outgoingTrafficRatio, double interfloorTrafficRatio,
                                 BatchArrivalProfile batchProfile) {
        this.population = population;
        this.incomingTrafficRatio = incomingTrafficRatio;
        this.outgoingTrafficRatio = outgoingTrafficRatio;
        this.interfloorTrafficRatio = interfloorTrafficRatio;
        this.batchProfile = batchProfile;
    }
    
    // Getter方法
    public int getPopulation() { return population; }
    public double getIncomingTrafficRatio() { return incomingTrafficRatio; }
    public double getOutgoingTrafficRatio() { return outgoingTrafficRatio; }
    public double getInterfloorTrafficRatio() { return interfloorTrafficRatio; }
    public BatchArrivalProfile getBatchProfile() { return batchProfile; }
}