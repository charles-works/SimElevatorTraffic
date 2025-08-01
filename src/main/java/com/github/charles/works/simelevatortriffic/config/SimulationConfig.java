package com.github.charles.works.simelevatortriffic.config;

/**
 * 仿真配置类
 */
public class SimulationConfig {
    private long duration;
    private int timeSlice;
    private String startTime;
    private String endTime;
    private String odMatrixEstimationMethod;
    
    // 构造函数
    public SimulationConfig() {}
    
    // Getter和Setter方法
    public long getDuration() { return duration; }
    public void setDuration(long duration) { this.duration = duration; }
    
    public int getTimeSlice() { return timeSlice; }
    public void setTimeSlice(int timeSlice) { this.timeSlice = timeSlice; }
    
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    
    public String getOdMatrixEstimationMethod() { return odMatrixEstimationMethod; }
    public void setOdMatrixEstimationMethod(String odMatrixEstimationMethod) { this.odMatrixEstimationMethod = odMatrixEstimationMethod; }
}