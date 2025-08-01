package com.github.charles.works.simelevatortriffic.service;

import com.github.charles.works.simelevatortriffic.domain.*;

import java.util.List;

/**
 * 乘客流量生成服务，基于PDF研究实现高保真度的乘客到达模型
 * "批次到达可以建模为时间非齐次泊松过程"
 */
public interface PassengerFlowService {
    
    /**
     * 生成指定时间段的乘客到达事件
     * 
     * @param building 建筑模型
     * @param startTime 仿真开始时间(秒)
     * @param duration 生成时长(秒)
     * @return 乘客到达事件列表
     */
    List<PassengerArrivalEvent> generatePassengerFlow(Building building, long startTime, long duration);
    
    /**
     * 生成符合批次特性的到达事件
     * 
     * @param floor 楼层
     * @param timePeriod 时间段
     * @param startTime 开始时间
     * @param duration 时长
     * @return 批次到达事件列表
     */
    List<BatchArrivalEvent> generateBatchArrivals(Floor floor, TimePeriod timePeriod, long startTime, long duration);
    
    /**
     * 基于OD矩阵生成乘客目的地
     * 
     * @param originFloor 起点楼层
     * @param odMatrix OD矩阵
     * @param passengerCount 乘客数量
     * @return 乘客目的地列表
     */
    List<Integer> generateDestinations(int originFloor, ODMetricMatrix odMatrix, int passengerCount);
}