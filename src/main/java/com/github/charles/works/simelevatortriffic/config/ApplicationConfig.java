package com.github.charles.works.simelevatortriffic.config;

/**
 * 应用配置类，管理系统的各种配置参数
 */
public class ApplicationConfig {
    // 数据库配置
    private DatabaseConfig databaseConfig;
    
    // 仿真配置
    private SimConfig simulationConfig;
    
    // API配置
    private ApiConfig apiConfig;
    
    // 日志配置
    private LogConfig logConfig;
    
    // 构造函数
    public ApplicationConfig() {
        this.databaseConfig = new DatabaseConfig();
        this.simulationConfig = new SimConfig();
        this.apiConfig = new ApiConfig();
        this.logConfig = new LogConfig();
    }
    
    // getter和setter方法
    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }
    
    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }
    
    public SimConfig getSimulationConfig() {
        return simulationConfig;
    }
    
    public void setSimulationConfig(SimConfig simulationConfig) {
        this.simulationConfig = simulationConfig;
    }
    
    public ApiConfig getApiConfig() {
        return apiConfig;
    }
    
    public void setApiConfig(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }
    
    public LogConfig getLogConfig() {
        return logConfig;
    }
    
    public void setLogConfig(LogConfig logConfig) {
        this.logConfig = logConfig;
    }
}