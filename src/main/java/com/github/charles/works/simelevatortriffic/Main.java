package com.github.charles.works.simelevatortriffic;

import com.github.charles.works.simelevatortriffic.api.SimulationApi;
import com.github.charles.works.simelevatortriffic.config.ApplicationConfig;
import com.github.charles.works.simelevatortriffic.service.SimulationService;

/**
 * 电梯交通量仿真分析软件主类
 * 
 * 根据开发文档要求实现：
 * 1. 支持4种电梯控制方式的精确仿真：单控、并联、群控、目的选层
 * 2. 模拟不同建筑类型(办公楼、商场、住宅、混合用途)的交通特性
 * 3. 建模早高峰、午餐高峰、晚高峰和闲时等不同时段的交通模式
 * 4. 支持乘客批次到达模型，符合"时间非齐次泊松过程"特性
 * 5. 实现OD矩阵估计方法(LP、BILS、CP算法)
 * 6. 提供详细的性能指标分析，包括等待时间、运输时间、系统利用率等
 * 7. 支持紧急疏散场景的仿真
 * 8. 提供API接口，可与其他建筑管理系统集成
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("SimElevatorTriffic - 电梯交通量仿真分析软件");
        System.out.println("Version: 1.0-SNAPSHOT");
        System.out.println("Group: com.github.charles-works");
        System.out.println("Java Version: 21");
        System.out.println("");
        System.out.println("系统启动中...");
        
        try {
            // 初始化应用配置
            ApplicationConfig config = new ApplicationConfig();
            
            // 初始化核心服务
            SimulationService simulationService = new SimulationService(config);
            
            // 初始化并启动Web API服务
            SimulationApi api = new SimulationApi(simulationService);
            api.start(config.getApiConfig().getPort());
            
            System.out.println("系统已启动，请使用Web API进行操作。");
            System.out.println("API地址: http://localhost:" + config.getApiConfig().getPort() + "/api/v1");
            
            // 注册关闭钩子
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("正在关闭系统...");
                api.stop();
                simulationService.shutdown();
                System.out.println("系统已关闭。");
            }));
            
        } catch (Exception e) {
            System.err.println("系统启动失败: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}