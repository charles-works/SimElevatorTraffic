package com.github.charles.works.simelevatortriffic.api;

import com.github.charles.works.simelevatortriffic.domain.ElevatorStatistics;
import com.github.charles.works.simelevatortriffic.domain.PassengerStatistics;
import com.github.charles.works.simelevatortriffic.domain.PassengerArrivalEvent;
import com.github.charles.works.simelevatortriffic.domain.ODMetricMatrix;
import java.util.List;

/**
 * 完整仿真结果
 */
public class FullSimulationResult {
    private String simulationId;
    private long startTime;
    private long endTime;
    private List<ElevatorStatistics> elevatorStatistics;
    private PassengerStatistics passengerStatistics;
    private List<PassengerArrivalEvent> passengerEvents;
    private ODMetricMatrix odMatrix;
    private String report;
    
    public FullSimulationResult() {}
    
    public FullSimulationResult(String simulationId, long startTime, long endTime,
                               List<ElevatorStatistics> elevatorStatistics,
                               PassengerStatistics passengerStatistics,
                               List<PassengerArrivalEvent> passengerEvents,
                               ODMetricMatrix odMatrix,
                               String report) {
        this.simulationId = simulationId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.elevatorStatistics = elevatorStatistics;
        this.passengerStatistics = passengerStatistics;
        this.passengerEvents = passengerEvents;
        this.odMatrix = odMatrix;
        this.report = report;
    }
    
    // Getter和Setter方法
    public String getSimulationId() { return simulationId; }
    public void setSimulationId(String simulationId) { this.simulationId = simulationId; }
    
    public long getStartTime() { return startTime; }
    public void setStartTime(long startTime) { this.startTime = startTime; }
    
    public long getEndTime() { return endTime; }
    public void setEndTime(long endTime) { this.endTime = endTime; }
    
    public List<ElevatorStatistics> getElevatorStatistics() { return elevatorStatistics; }
    public void setElevatorStatistics(List<ElevatorStatistics> elevatorStatistics) { this.elevatorStatistics = elevatorStatistics; }
    
    public PassengerStatistics getPassengerStatistics() { return passengerStatistics; }
    public void setPassengerStatistics(PassengerStatistics passengerStatistics) { this.passengerStatistics = passengerStatistics; }
    
    public List<PassengerArrivalEvent> getPassengerEvents() { return passengerEvents; }
    public void setPassengerEvents(List<PassengerArrivalEvent> passengerEvents) { this.passengerEvents = passengerEvents; }
    
    public ODMetricMatrix getOdMatrix() { return odMatrix; }
    public void setOdMatrix(ODMetricMatrix odMatrix) { this.odMatrix = odMatrix; }
    
    public String getReport() { return report; }
    public void setReport(String report) { this.report = report; }
}