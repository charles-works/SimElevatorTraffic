package com.github.charles.works.simelevatortriffic.domain;

/**
 * 仿真事件基类
 */
public abstract class SimulationEvent {
    
    protected final long timestamp; // 仿真时间戳(毫秒)
    protected final String buildingId;
    
    public SimulationEvent(long timestamp, String buildingId) {
        this.timestamp = timestamp;
        this.buildingId = buildingId;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public String getBuildingId() {
        return buildingId;
    }
}