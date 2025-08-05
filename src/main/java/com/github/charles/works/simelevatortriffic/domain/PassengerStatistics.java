package com.github.charles.works.simelevatortriffic.domain;

/**
 * 乘客统计数据
 */
public class PassengerStatistics {
    private final int totalPassengers;
    private final double averageWaitTime;
    private final double averageTravelTime;
    private final int maxWaitTime;
    
    public PassengerStatistics(int totalPassengers, double averageWaitTime,
                              double averageTravelTime, int maxWaitTime) {
        this.totalPassengers = totalPassengers;
        this.averageWaitTime = averageWaitTime;
        this.averageTravelTime = averageTravelTime;
        this.maxWaitTime = maxWaitTime;
    }
    
    // Getter方法
    public int getTotalPassengers() { return totalPassengers; }
    public double getAverageWaitTime() { return averageWaitTime; }
    public double getAverageTravelTime() { return averageTravelTime; }
    public int getMaxWaitTime() { return maxWaitTime; }
}