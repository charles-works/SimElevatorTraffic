package com.github.charles.works.simelevatortriffic.domain;

/**
 * 批次大小概率类
 */
public class BatchSizeProbability {
    private final int batchSize;
    private final double probability;
    
    public BatchSizeProbability(int batchSize, double probability) {
        this.batchSize = batchSize;
        this.probability = probability;
    }
    
    // Getter方法
    public int getBatchSize() { return batchSize; }
    public double getProbability() { return probability; }
}