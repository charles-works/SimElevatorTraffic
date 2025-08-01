package com.github.charles.works.simelevatortriffic.config;

/**
 * API配置
 */
public class ApiConfig {
    private int port = 7000;
    private String contextPath = "/api/v1";
    private long requestTimeoutMs = 30000; // 30秒
    private int maxRequestSizeMb = 10; // 10MB
    
    public ApiConfig() {}
    
    // getter和setter方法
    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }
    
    public String getContextPath() { return contextPath; }
    public void setContextPath(String contextPath) { this.contextPath = contextPath; }
    
    public long getRequestTimeoutMs() { return requestTimeoutMs; }
    public void setRequestTimeoutMs(long requestTimeoutMs) { this.requestTimeoutMs = requestTimeoutMs; }
    
    public int getMaxRequestSizeMb() { return maxRequestSizeMb; }
    public void setMaxRequestSizeMb(int maxRequestSizeMb) { this.maxRequestSizeMb = maxRequestSizeMb; }
}