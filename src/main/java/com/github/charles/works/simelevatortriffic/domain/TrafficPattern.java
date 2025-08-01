package com.github.charles.works.simelevatortriffic.domain;

import java.util.*;

/**
 * 交通模式，实时识别当前主导交通流向
 * 根据PDF中"交通模式比例"的研究实现
 */
public class TrafficPattern {
    private final double incomingRatio;
    private final double outgoingRatio;
    private final double interfloorRatio;
    private final ElevatorDirection dominantDirection;
    private final long lastUpdated;
    
    public TrafficPattern(double incomingRatio, double outgoingRatio, double interfloorRatio,
                         ElevatorDirection dominantDirection, long lastUpdated) {
        this.incomingRatio = incomingRatio;
        this.outgoingRatio = outgoingRatio;
        this.interfloorRatio = interfloorRatio;
        this.dominantDirection = dominantDirection;
        this.lastUpdated = lastUpdated;
    }
    
    public static TrafficPattern initialPattern() {
        return new TrafficPattern(0.0, 0.0, 0.0, ElevatorDirection.STOPPED, System.currentTimeMillis());
    }
    
    // Getter方法
    public double getIncomingRatio() { return incomingRatio; }
    public double getOutgoingRatio() { return outgoingRatio; }
    public double getInterfloorRatio() { return interfloorRatio; }
    public ElevatorDirection getDominantDirection() { return dominantDirection; }
    public long getLastUpdated() { return lastUpdated; }
}