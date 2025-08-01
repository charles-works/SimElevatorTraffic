package com.github.charles.works.simelevatortriffic.config;

/**
 * 日志配置
 */
public class LogConfig {
    private String level = "INFO";
    private String pattern = "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n";
    private String file = "logs/sim-elevator.log";
    private int maxFileSizeMb = 100;
    private int maxHistory = 30;
    
    public LogConfig() {}
    
    // getter和setter方法
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    
    public String getPattern() { return pattern; }
    public void setPattern(String pattern) { this.pattern = pattern; }
    
    public String getFile() { return file; }
    public void setFile(String file) { this.file = file; }
    
    public int getMaxFileSizeMb() { return maxFileSizeMb; }
    public void setMaxFileSizeMb(int maxFileSizeMb) { this.maxFileSizeMb = maxFileSizeMb; }
    
    public int getMaxHistory() { return maxHistory; }
    public void setMaxHistory(int maxHistory) { this.maxHistory = maxHistory; }
}