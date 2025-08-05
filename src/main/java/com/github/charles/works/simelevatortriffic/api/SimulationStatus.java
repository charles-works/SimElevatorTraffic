package com.github.charles.works.simelevatortriffic.api;

/**
 * 仿真状态
 */
public class SimulationStatus {
    private String simulationId;
    private String status; // RUNNING, COMPLETED, FAILED, PAUSED
    private long startTime;
    private long currentTime;
    private int progress; // 0-100
    private String message;
    
    public SimulationStatus() {
        this.status = "UNKNOWN";
        this.progress = 0;
    }
    
    public SimulationStatus(String simulationId, String status, long startTime, long currentTime, int progress, String message) {
        this.simulationId = simulationId;
        this.status = status;
        this.startTime = startTime;
        this.currentTime = currentTime;
        this.progress = progress;
        this.message = message;
    }
    
    // Getter和Setter方法
    public String getSimulationId() { return simulationId; }
    public void setSimulationId(String simulationId) { this.simulationId = simulationId; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public long getStartTime() { return startTime; }
    public void setStartTime(long startTime) { this.startTime = startTime; }
    
    public long getCurrentTime() { return currentTime; }
    public void setCurrentTime(long currentTime) { this.currentTime = currentTime; }
    
    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}