package com.github.charles.works.simelevatortriffic.api;

import com.github.charles.works.simelevatortriffic.domain.ElevatorStatistics;
import com.github.charles.works.simelevatortriffic.domain.PassengerStatistics;
import java.util.List;
import java.util.Map;

/**
 * 时间线数据
 */
public class TimelineData {
    private String simulationId;
    private int fromTime;
    private int toTime;
    private int timeSlice;
    private List<ElevatorStatistics> elevatorStatsTimeline;
    private PassengerStatistics passengerStatsTimeline;
    private Map<String, Object> additionalData;
    
    public TimelineData() {}
    
    public TimelineData(String simulationId, int fromTime, int toTime, int timeSlice,
                       List<ElevatorStatistics> elevatorStatsTimeline,
                       PassengerStatistics passengerStatsTimeline,
                       Map<String, Object> additionalData) {
        this.simulationId = simulationId;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.timeSlice = timeSlice;
        this.elevatorStatsTimeline = elevatorStatsTimeline;
        this.passengerStatsTimeline = passengerStatsTimeline;
        this.additionalData = additionalData;
    }
    
    // Getter和Setter方法
    public String getSimulationId() { return simulationId; }
    public void setSimulationId(String simulationId) { this.simulationId = simulationId; }
    
    public int getFromTime() { return fromTime; }
    public void setFromTime(int fromTime) { this.fromTime = fromTime; }
    
    public int getToTime() { return toTime; }
    public void setToTime(int toTime) { this.toTime = toTime; }
    
    public int getTimeSlice() { return timeSlice; }
    public void setTimeSlice(int timeSlice) { this.timeSlice = timeSlice; }
    
    public List<ElevatorStatistics> getElevatorStatsTimeline() { return elevatorStatsTimeline; }
    public void setElevatorStatsTimeline(List<ElevatorStatistics> elevatorStatsTimeline) { this.elevatorStatsTimeline = elevatorStatsTimeline; }
    
    public PassengerStatistics getPassengerStatsTimeline() { return passengerStatsTimeline; }
    public void setPassengerStatsTimeline(PassengerStatistics passengerStatsTimeline) { this.passengerStatsTimeline = passengerStatsTimeline; }
    
    public Map<String, Object> getAdditionalData() { return additionalData; }
    public void setAdditionalData(Map<String, Object> additionalData) { this.additionalData = additionalData; }
}