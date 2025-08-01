package com.github.charles.works.simelevatortriffic.api;

/**
 * 验证错误
 */
public class ValidationError {
    private String field;
    private String message;
    
    public ValidationError() {}
    
    public ValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }
    
    // getter和setter方法
    public String getField() { return field; }
    public void setField(String field) { this.field = field; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}