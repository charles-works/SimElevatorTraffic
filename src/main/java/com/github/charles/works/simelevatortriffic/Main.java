package com.github.charles.works.simelevatortriffic;

import com.github.charles.works.simelevatortriffic.service.SimulationService;
import com.github.charles.works.simelevatortriffic.config.ApplicationConfig;
import com.github.charles.works.simelevatortriffic.api.SimulationApi;

/**
 * 主类，用于启动电梯仿真系统Web API服务
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("开始启动电梯仿真系统...");
        
        // 创建应用配置
        ApplicationConfig config = new ApplicationConfig();
        
        // 创建仿真服务
        SimulationService simulationService = new SimulationService(config);
        
        // 创建并启动API服务
        SimulationApi api = new SimulationApi(simulationService);
        int port = config.getApiConfig().getPort();
        
        System.out.println("正在启动Web API服务，端口: " + port);
        System.out.println("API服务将保持运行，按 Ctrl+C 停止服务");
        
        try {
            api.start(port);
            
            // 保持主线程运行
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            System.out.println("服务被中断，正在关闭...");
            api.stop();
            simulationService.shutdown();
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("启动服务时发生错误: " + e.getMessage());
            e.printStackTrace();
            simulationService.shutdown();
        }
    }
}