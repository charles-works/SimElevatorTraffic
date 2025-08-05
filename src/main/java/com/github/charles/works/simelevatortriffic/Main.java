package com.github.charles.works.simelevatortriffic;

import com.github.charles.works.simelevatortriffic.service.SimulationService;
import com.github.charles.works.simelevatortriffic.config.ApplicationConfig;

/**
 * 主类，用于测试和完善代码
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("开始测试电梯仿真系统...");
        
        // 创建应用配置
        ApplicationConfig config = new ApplicationConfig();
        
        // 创建仿真服务
        SimulationService simulationService = new SimulationService(config);
        
        // 测试服务关闭功能
        simulationService.shutdown();
        
        System.out.println("测试完成。");
    }
}