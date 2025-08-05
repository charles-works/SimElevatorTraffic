package com.github.charles.works.simelevatortriffic.domain;

/**
 * 电梯统计数据
 */
public class ElevatorStatistics {
    private final String elevatorId;
    private final int totalTrips;
    private final double averageWaitTime;
    private final double averageTravelTime;
    private final double utilizationRate;
    
    public ElevatorStatistics(String elevatorId, int totalTrips, double averageWaitTime,
                             double averageTravelTime, double utilizationRate) {
        this.elevatorId = elevatorId;
        this.totalTrips = totalTrips;
        this.averageWaitTime = averageWaitTime;
        this.averageTravelTime = averageTravelTime;
        this.utilizationRate = utilizationRate;
    }
    
    // Getter方法
    public String getElevatorId() { return elevatorId; }
    public int getTotalTrips() { return totalTrips; }
    public double getAverageWaitTime() { return averageWaitTime; }
    public double getAverageTravelTime() { return averageTravelTime; }
    public double getUtilizationRate() { return utilizationRate; }
}