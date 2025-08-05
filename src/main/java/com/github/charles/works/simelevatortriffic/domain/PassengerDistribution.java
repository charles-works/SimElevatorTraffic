package com.github.charles.works.simelevatortriffic.domain;

import java.util.Map;

/**
 * 乘客分布，记录各楼层的乘客数量
 */
public class PassengerDistribution {
    private final Map<Integer, Integer> waitingPassengersByFloor;
    private final Map<Integer, Integer> travelingPassengersByFloor;
    
    public PassengerDistribution(Map<Integer, Integer> waitingPassengersByFloor,
                                Map<Integer, Integer> travelingPassengersByFloor) {
        this.waitingPassengersByFloor = waitingPassengersByFloor;
        this.travelingPassengersByFloor = travelingPassengersByFloor;
    }
    
    // Getter方法
    public Map<Integer, Integer> getWaitingPassengersByFloor() { return waitingPassengersByFloor; }
    public Map<Integer, Integer> getTravelingPassengersByFloor() { return travelingPassengersByFloor; }
}