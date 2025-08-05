package com.github.charles.works.simelevatortriffic.service;

import com.github.charles.works.simelevatortriffic.domain.*;
import java.util.*;

/**
 * 仿真调度器，负责执行仿真逻辑
 */
public class SimulationScheduler {
    private final Building building;
    private final List<ElevatorStatistics> elevatorStatistics;
    private PassengerStatistics passengerStatistics;
    
    public SimulationScheduler(Building building) {
        this.building = building;
        this.elevatorStatistics = new ArrayList<>();
        this.passengerStatistics = new PassengerStatistics(0, 0.0, 0.0, 0);
    }
    
    /**
     * 运行仿真
     * @param duration 仿真持续时间(毫秒)
     */
    public void runSimulation(long duration) {
        System.out.println("开始运行仿真，持续时间: " + duration + "ms");
        
        // 初始化仿真
        initializeSimulation();
        
        // 运行仿真逻辑
        long startTime = System.currentTimeMillis();
        long endTime = startTime + duration;
        long currentTime = startTime;
        
        // 简化的仿真循环
        while (currentTime < endTime) {
            // 更新电梯状态
            updateElevators(currentTime);
            
            // 生成乘客流量
            generatePassengerFlow(currentTime);
            
            // 处理电梯调度
            handleElevatorScheduling(currentTime);
            
            // 更新时间
            currentTime += 1000; // 每秒更新一次
            
            // 模拟处理时间
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        // 收集统计数据
        collectStatistics();
        
        System.out.println("仿真运行完成");
    }
    
    /**
     * 初始化仿真
     */
    private void initializeSimulation() {
        System.out.println("初始化仿真环境");
        // 可以在这里初始化建筑、电梯等
    }
    
    /**
     * 更新电梯状态
     */
    private void updateElevators(long currentTime) {
        // 遍历所有电梯组
        for (ElevatorGroup group : building.getElevatorGroups()) {
            // 遍历组内所有电梯
            for (Elevator elevator : group.getElevators()) {
                // 更新电梯状态（简化实现）
                // 实际实现应该根据电梯控制算法更新电梯状态
            }
        }
    }
    
    /**
     * 生成乘客流量
     */
    private void generatePassengerFlow(long currentTime) {
        // 生成乘客流量（简化实现）
        // 实际实现应该根据建筑类型、时间等因素生成乘客到达事件
    }
    
    /**
     * 处理电梯调度
     */
    private void handleElevatorScheduling(long currentTime) {
        // 处理电梯调度逻辑（简化实现）
        // 实际实现应该根据乘客请求、电梯状态等进行调度
    }
    
    /**
     * 收集统计数据
     */
    private void collectStatistics() {
        System.out.println("收集仿真统计数据");
        
        // 收集电梯统计数据
        elevatorStatistics.clear();
        for (ElevatorGroup group : building.getElevatorGroups()) {
            for (Elevator elevator : group.getElevators()) {
                // 创建模拟统计数据
                ElevatorStatistics stats = new ElevatorStatistics(
                    elevator.getId(),
                    new Random().nextInt(100), // 随机生成总行程数
                    10.0 + new Random().nextDouble() * 30.0, // 随机生成平均等待时间
                    20.0 + new Random().nextDouble() * 60.0, // 随机生成平均旅行时间
                    0.5 + new Random().nextDouble() * 0.5 // 随机生成利用率
                );
                elevatorStatistics.add(stats);
            }
        }
        
        // 收集乘客统计数据
        passengerStatistics = new PassengerStatistics(
            new Random().nextInt(1000), // 随机生成总乘客数
            15.0 + new Random().nextDouble() * 25.0, // 随机生成平均等待时间
            30.0 + new Random().nextDouble() * 50.0, // 随机生成平均旅行时间
            60 + new Random().nextInt(120) // 随机生成最大等待时间
        );
    }
    
    // Getter方法
    public List<ElevatorStatistics> getElevatorStatistics() {
        return new ArrayList<>(elevatorStatistics);
    }
    
    public PassengerStatistics getPassengerStatistics() {
        return passengerStatistics;
    }
}