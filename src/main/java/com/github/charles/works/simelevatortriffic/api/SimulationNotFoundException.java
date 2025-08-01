package com.github.charles.works.simelevatortriffic.api;

/**
 * 仿真未找到异常
 */
public class SimulationNotFoundException extends Exception {
    public SimulationNotFoundException() {}
    
    public SimulationNotFoundException(String message) {
        super(message);
    }
    
    public SimulationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}