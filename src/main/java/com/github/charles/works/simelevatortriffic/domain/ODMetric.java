package com.github.charles.works.simelevatortriffic.domain;

/**
 * OD矩阵，表示起点到终点的乘客流量
 * 实现PDF中描述的电梯行程OD矩阵估计
 */
public class ODMetric {
    private final int originFloor;
    private final int destinationFloor;
    private final int passengerCount;
    private final double probability;
    
    public ODMetric(int originFloor, int destinationFloor, int passengerCount, double probability) {
        this.originFloor = originFloor;
        this.destinationFloor = destinationFloor;
        this.passengerCount = passengerCount;
        this.probability = probability;
    }
    
    // Getter方法
    public int getOriginFloor() { return originFloor; }
    public int getDestinationFloor() { return destinationFloor; }
    public int getPassengerCount() { return passengerCount; }
    public double getProbability() { return probability; }
    
    public ODMetric withPassengerCount(int newCount) {
        return new ODMetric(originFloor, destinationFloor, newCount, probability);
    }
}