package com.github.charles.works.simelevatortriffic.api;

import com.github.charles.works.simelevatortriffic.domain.ElevatorStatistics;
import com.github.charles.works.simelevatortriffic.domain.PassengerStatistics;

import java.util.List;

/**
 * 仿真结果
 */
public class SimulationResult {
    private final String simulationId;
    private final long startTime;
    private final long endTime;
    private final long realDuration;
    private final List<ElevatorStatistics> elevatorStatistics;
    private final PassengerStatistics passengerStatistics;
    
    public SimulationResult(String simulationId, long startTime, long endTime, 
                           long realDuration, List<ElevatorStatistics> elevatorStatistics,
                           PassengerStatistics passengerStatistics) {
        this.simulationId = simulationId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.realDuration = realDuration;
        this.elevatorStatistics = elevatorStatistics;
        this.passengerStatistics = passengerStatistics;
    }
    
    // Getter方法
    public String getSimulationId() { return simulationId; }
    public long getStartTime() { return startTime; }
    public long getEndTime() { return endTime; }
    public long getDuration() { return endTime - startTime; }
    public long getRealDuration() { return realDuration; }
    public List<ElevatorStatistics> getElevatorStatistics() { return elevatorStatistics; }
    public PassengerStatistics getPassengerStatistics() { return passengerStatistics; }
}