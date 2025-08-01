package com.github.charles.works.simelevatortriffic.domain;

/**
 * 乘客对象，跟踪每个乘客的完整旅程
 * 基于PDF中"乘客旅程"的定义设计
 */
public class Passenger {
    private final String id;
    private final int originFloor;
    private final int destinationFloor;
    private final long arrivalTime;         // 到达电梯厅时间(仿真时间戳)
    private final long boardingTime;        // 进入电梯时间
    private final long alightingTime;       // 离开电梯时间
    private final PassengerType type;
    private final int batchSize;            // 所属批次大小
    
    public Passenger(String id, int originFloor, int destinationFloor, 
                    long arrivalTime, long boardingTime, long alightingTime,
                    PassengerType type, int batchSize) {
        this.id = id;
        this.originFloor = originFloor;
        this.destinationFloor = destinationFloor;
        this.arrivalTime = arrivalTime;
        this.boardingTime = boardingTime;
        this.alightingTime = alightingTime;
        this.type = type;
        this.batchSize = batchSize;
    }
    
    // Getter方法
    public String getId() { return id; }
    public int getOriginFloor() { return originFloor; }
    public int getDestinationFloor() { return destinationFloor; }
    public long getArrivalTime() { return arrivalTime; }
    public long getBoardingTime() { return boardingTime; }
    public long getAlightingTime() { return alightingTime; }
    public PassengerType getType() { return type; }
    public int getBatchSize() { return batchSize; }
}