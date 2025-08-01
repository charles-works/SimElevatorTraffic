package com.github.charles.works.simelevatortriffic.api;

/**
 * API响应结构
 */
public class ApiResponse<T> {
    private String status;
    private int code;
    private String message;
    private T data;
    private long timestamp;
    
    public ApiResponse(String status, int code, String message, T data, long timestamp) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }
    
    // 默认构造函数
    public ApiResponse() {}
    
    // getter和setter方法
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}