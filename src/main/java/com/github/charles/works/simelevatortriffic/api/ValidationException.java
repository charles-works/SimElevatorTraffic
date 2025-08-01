package com.github.charles.works.simelevatortriffic.api;

/**
 * 验证异常
 */
public class ValidationException extends Exception {
    private java.util.List<ValidationError> errors;
    
    public ValidationException() {}
    
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(java.util.List<ValidationError> errors) {
        this.errors = errors;
    }
    
    public ValidationException(String message, java.util.List<ValidationError> errors) {
        super(message);
        this.errors = errors;
    }
    
    public java.util.List<ValidationError> getErrors() {
        return errors;
    }
    
    public void setErrors(java.util.List<ValidationError> errors) {
        this.errors = errors;
    }
}