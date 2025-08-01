package com.github.charles.works.simelevatortriffic.domain;

import java.util.*;

/**
 * 批次到达特性，根据PDF研究实现
 * "批次到达可以建模为时间非齐次泊松过程"
 */
public class BatchArrivalProfile {
    private final double batchProbability;     // 批次到达概率
    private final List<BatchSizeProbability> batchSizeDistribution;
    private final double interArrivalMean;     // 批次间平均间隔(秒)
    private final double interArrivalStdDev;   // 批次间间隔标准差
    
    public BatchArrivalProfile(double batchProbability, 
                              List<BatchSizeProbability> batchSizeDistribution,
                              double interArrivalMean, double interArrivalStdDev) {
        this.batchProbability = batchProbability;
        this.batchSizeDistribution = new ArrayList<>(batchSizeDistribution);
        this.interArrivalMean = interArrivalMean;
        this.interArrivalStdDev = interArrivalStdDev;
    }
    
    // Getter方法
    public double getBatchProbability() { return batchProbability; }
    public List<BatchSizeProbability> getBatchSizeDistribution() { 
        return new ArrayList<>(batchSizeDistribution); 
    }
    public double getInterArrivalMean() { return interArrivalMean; }
    public double getInterArrivalStdDev() { return interArrivalStdDev; }
}