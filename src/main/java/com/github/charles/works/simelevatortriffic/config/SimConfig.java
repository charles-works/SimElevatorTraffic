package com.github.charles.works.simelevatortriffic.config;

/**
 * 仿真配置
 */
public class SimConfig {
    private int maxConcurrentSimulations = 10;
    private long defaultSimulationDuration = 3600; // 1小时(秒)
    private int defaultTimeSlice = 5; // 5秒
    private String defaultODEstimationMethod = "BILS";
    private boolean enableRandomizationForBILS = true;
    private long maxExecutionTimeMs = 500; // 500毫秒
    
    public SimConfig() {}
    
    // getter和setter方法
    public int getMaxConcurrentSimulations() { return maxConcurrentSimulations; }
    public void setMaxConcurrentSimulations(int maxConcurrentSimulations) { this.maxConcurrentSimulations = maxConcurrentSimulations; }
    
    public long getDefaultSimulationDuration() { return defaultSimulationDuration; }
    public void setDefaultSimulationDuration(long defaultSimulationDuration) { this.defaultSimulationDuration = defaultSimulationDuration; }
    
    public int getDefaultTimeSlice() { return defaultTimeSlice; }
    public void setDefaultTimeSlice(int defaultTimeSlice) { this.defaultTimeSlice = defaultTimeSlice; }
    
    public String getDefaultODEstimationMethod() { return defaultODEstimationMethod; }
    public void setDefaultODEstimationMethod(String defaultODEstimationMethod) { this.defaultODEstimationMethod = defaultODEstimationMethod; }
    
    public boolean isEnableRandomizationForBILS() { return enableRandomizationForBILS; }
    public void setEnableRandomizationForBILS(boolean enableRandomizationForBILS) { this.enableRandomizationForBILS = enableRandomizationForBILS; }
    
    public long getMaxExecutionTimeMs() { return maxExecutionTimeMs; }
    public void setMaxExecutionTimeMs(long maxExecutionTimeMs) { this.maxExecutionTimeMs = maxExecutionTimeMs; }
}