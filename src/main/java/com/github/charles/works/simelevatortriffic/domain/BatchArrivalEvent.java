package com.github.charles.works.simelevatortriffic.domain;

/**
 * 批次到达事件
 */
public final class BatchArrivalEvent extends SimulationEvent {
    private final int floor;
    private final int batchSize;
    
    public BatchArrivalEvent(long timestamp, String buildingId, int floor, int batchSize) {
        super(timestamp, buildingId);
        this.floor = floor;
        this.batchSize = batchSize;
    }
    
    public int getFloor() {
        return floor;
    }
    
    public int getBatchSize() {
        return batchSize;
    }
}