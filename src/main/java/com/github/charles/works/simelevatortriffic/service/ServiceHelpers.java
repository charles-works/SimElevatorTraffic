package com.github.charles.works.simelevatortriffic.service;

import com.github.charles.works.simelevatortriffic.domain.*;

import java.util.List;

/**
 * 服务辅助类，包含各种记录类定义
 */
public class ServiceHelpers {
    // 私有构造函数防止实例化
    private ServiceHelpers() {}
    
    /**
     * 电梯行程记录
     */
    public record ElevatorTrip(
        String tripId,
        String elevatorId,
        long startTime,
        long endTime,
        ElevatorDirection direction,
        List<ElevatorStop> stops
    ) {}
    
    /**
     * 电梯停靠记录
     */
    public record ElevatorStop(
        int floor,
        long arrivalTime,
        long departureTime,
        int boardingCount,
        int alightingCount
    ) {}
    
    /**
     * 召唤请求记录
     */
    public record CallRequest(
        int floor,
        ElevatorDirection direction,
        String passengerId
    ) {
        public int getFloor() {
            return floor;
        }
        
        public ElevatorDirection getDirection() {
            return direction;
        }
        
        public String getPassengerId() {
            return passengerId;
        }
    }
    
    /**
     * 目的地请求记录
     */
    public record DestinationRequest(
        int originFloor,
        int destinationFloor,
        String passengerId
    ) {}
    
    /**
     * 分配结果记录
     */
    public record AssignmentResult(
        String elevatorId,
        double expectedWaitTime
    ) {}
    
    /**
     * 验证结果记录
     */
    public record ValidationResult(
        boolean isValid,
        List<ValidationIssue> issues
    ) {}
    
    /**
     * 验证问题记录
     */
    public record ValidationIssue(
        String type,
        String message
    ) {}
}