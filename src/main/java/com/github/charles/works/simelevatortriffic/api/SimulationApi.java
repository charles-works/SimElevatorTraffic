package com.github.charles.works.simelevatortriffic.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * API应用主类
 */
public class SimulationApi {
    private final Javalin app;
    private final SimulationController simulationController;
    
    public SimulationApi(com.github.charles.works.simelevatortriffic.service.SimulationService simulationService) {
        // 初始化Jackson ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        
        // 初始化控制器
        this.simulationController = new SimulationController(simulationService, objectMapper);
        
        // 创建Javalin应用
        this.app = Javalin.create(config -> {
            config.routing.ignoreTrailingSlashes = true;
        });
        
        // 配置路由
        setupRoutes();
    }
    
    private void setupRoutes() {
        // 仿真执行API
        app.post("/api/v1/simulate", simulationController::simulate);
        
        // 仿真状态查询API
        app.get("/api/v1/simulations/{id}", simulationController::getSimulationStatus);
        
        // 时间切片数据API
        app.get("/api/v1/simulations/{id}/timeline", simulationController::getTimelineData);
        
        // 仿真结果API
        app.get("/api/v1/simulations/{id}/results", simulationController::getSimulationResults);
        
        // PDF报告生成API
        app.get("/api/v1/simulations/{id}/report", simulationController::generatePdfReport);
    }
    
    public void start(int port) {
        app.start(port);
    }
    
    public void stop() {
        app.stop();
    }
}