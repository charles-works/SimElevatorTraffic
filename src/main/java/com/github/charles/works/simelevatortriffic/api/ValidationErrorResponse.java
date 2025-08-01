package com.github.charles.works.simelevatortriffic.api;

/**
 * 验证错误响应
 */
public class ValidationErrorResponse {
    private java.util.List<ValidationError> errors;
    
    public ValidationErrorResponse() {}
    
    public ValidationErrorResponse(java.util.List<ValidationError> errors) {
        this.errors = errors;
    }
    
    // getter和setter方法
    public java.util.List<ValidationError> getErrors() { return errors; }
    public void setErrors(java.util.List<ValidationError> errors) { this.errors = errors; }
}