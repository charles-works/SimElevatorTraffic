package com.github.charles.works.simelevatortriffic.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

/**
 * REST API控制器，提供电梯交通仿真分析服务
 */
public class SimulationController {
    private final com.github.charles.works.simelevatortriffic.service.SimulationService simulationService;
    private final ObjectMapper objectMapper;
    
    public SimulationController(com.github.charles.works.simelevatortriffic.service.SimulationService simulationService, ObjectMapper objectMapper) {
        this.simulationService = simulationService;
        this.objectMapper = objectMapper;
    }
    
    /**
     * 执行电梯交通仿真
     * POST /api/v1/simulate
     */
    public void simulate(Context ctx) {
        try {
            System.out.println("Received simulation request: " + ctx.body());
            
            // 解析请求体
            SimulationRequest request = ctx.bodyValidator(SimulationRequest.class)
                .get();
            
            System.out.println("Parsed request: " + request);
            
            // 执行仿真
            SimulationResult result = simulationService.runSimulation(request);
            
            System.out.println("Simulation completed successfully");
            
            // 返回结果
            ctx.json(new ApiResponse<SimulationResult>(
                "SUCCESS",
                200,
                "Simulation started successfully",
                result,
                System.currentTimeMillis()
            ));
        } catch (Exception e) {
            System.err.println("Error processing simulation request: " + e.getMessage());
            e.printStackTrace();
            
            ctx.status(500);
            ctx.json(new ApiResponse<Void>(
                "ERROR",
                500,
                "Internal server error: " + e.getMessage(),
                null,
                System.currentTimeMillis()
            ));
        }
    }
    
    /**
     * 查询仿真状态
     * GET /api/v1/simulations/{id}
     */
    public void getSimulationStatus(Context ctx) {
        String simulationId = ctx.pathParam("id");
        
        try {
            SimulationStatus status = simulationService.getSimulationStatus(simulationId);
            
            ctx.json(new ApiResponse<SimulationStatus>(
                "SUCCESS",
                200,
                "Simulation status retrieved successfully",
                status,
                System.currentTimeMillis()
            ));
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(new ApiResponse<Void>(
                "ERROR",
                500,
                "Internal server error",
                null,
                System.currentTimeMillis()
            ));
        }
    }
    
    /**
     * 获取时间切片数据
     * GET /api/v1/simulations/{id}/timeline
     */
    public void getTimelineData(Context ctx) {
        String simulationId = ctx.pathParam("id");
        int fromTime = ctx.queryParamAsClass("fromTime", Integer.class).getOrDefault(0);
        int toTime = ctx.queryParamAsClass("toTime", Integer.class).getOrDefault(Integer.MAX_VALUE);
        int timeSlice = ctx.queryParamAsClass("timeSlice", Integer.class).getOrDefault(5);
        
        try {
            TimelineData timelineData = simulationService.getTimelineData(simulationId, fromTime, toTime, timeSlice);
            
            ctx.json(new ApiResponse<TimelineData>(
                "SUCCESS",
                200,
                "Timeline data retrieved successfully",
                timelineData,
                System.currentTimeMillis()
            ));
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(new ApiResponse<Void>(
                "ERROR",
                500,
                "Internal server error",
                null,
                System.currentTimeMillis()
            ));
        }
    }
    
    /**
     * 获取仿真结果
     * GET /api/v1/simulations/{id}/results
     */
    public void getSimulationResults(Context ctx) {
        String simulationId = ctx.pathParam("id");
        
        try {
            FullSimulationResult result = simulationService.getFullResults(simulationId);
            
            ctx.json(new ApiResponse<FullSimulationResult>(
                "SUCCESS",
                200,
                "Simulation results retrieved successfully",
                result,
                System.currentTimeMillis()
            ));
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(new ApiResponse<Void>(
                "ERROR",
                500,
                "Internal server error",
                null,
                System.currentTimeMillis()
            ));
        }
    }
    
    /**
     * 生成PDF报告
     * GET /api/v1/simulations/{id}/report
     */
    public void generatePdfReport(Context ctx) {
        String simulationId = ctx.pathParam("id");
        String reportType = ctx.queryParamAsClass("reportType", String.class).getOrDefault("DETAILED");
        boolean includeCharts = ctx.queryParamAsClass("includeCharts", Boolean.class).getOrDefault(true);
        
        try {
            byte[] pdfReport = simulationService.generatePdfReport(simulationId, reportType, includeCharts);
            
            ctx.contentType("application/pdf");
            ctx.header("Content-Disposition", "inline; filename=\"simulation_report.pdf\"");
            ctx.result(pdfReport);
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(new ApiResponse<Void>(
                "ERROR",
                500,
                "Internal server error",
                null,
                System.currentTimeMillis()
            ));
        }
    }
}