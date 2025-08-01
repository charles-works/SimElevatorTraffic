package com.github.charles.works.simelevatortriffic.domain;

import java.util.*;

/**
 * OD矩阵，表示起点到终点的乘客流量
 * 实现PDF中描述的电梯行程OD矩阵估计
 */
public class ODMetricMatrix {
    private final List<ODMetric> metrics;
    
    public ODMetricMatrix() {
        this.metrics = new ArrayList<>();
    }
    
    public ODMetricMatrix(List<ODMetric> metrics) {
        this.metrics = new ArrayList<>(metrics);
    }
    
    // Getter方法
    public List<ODMetric> getMetrics() { return new ArrayList<>(metrics); }
    
    // 提供矩阵操作方法
    public double getProbability(int origin, int destination) {
        return metrics.stream()
                .filter(m -> m.getOriginFloor() == origin && m.getDestinationFloor() == destination)
                .mapToDouble(ODMetric::getProbability)
                .findFirst()
                .orElse(0.0);
    }
    
    public int getTotalPassengers() {
        return metrics.stream()
                .mapToInt(ODMetric::getPassengerCount)
                .sum();
    }
    
    public ODMetric get(int originFloor, int destinationFloor) {
        return metrics.stream()
                .filter(m -> m.getOriginFloor() == originFloor && m.getDestinationFloor() == destinationFloor)
                .findFirst()
                .orElse(null);
    }
    
    public void add(ODMetric metric) {
        metrics.add(metric);
    }
    
    public void recalculateProbabilities() {
        int total = getTotalPassengers();
        if (total > 0) {
            for (int i = 0; i < metrics.size(); i++) {
                ODMetric metric = metrics.get(i);
                double probability = (double) metric.getPassengerCount() / total;
                metrics.set(i, new ODMetric(metric.getOriginFloor(), metric.getDestinationFloor(), 
                                           metric.getPassengerCount(), probability));
            }
        }
    }
    
    public int getBuildingFloors() {
        return metrics.stream()
                .mapToInt(m -> Math.max(m.getOriginFloor(), m.getDestinationFloor()))
                .max()
                .orElse(0);
    }
}