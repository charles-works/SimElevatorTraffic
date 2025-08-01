package com.github.charles.works.simelevatortriffic.domain;

/**
 * 时间段枚举
 */
public enum TimePeriod {
    MORNING_PEAK,   // 早高峰
    LUNCH_PEAK,     // 午餐高峰
    EVENING_PEAK,   // 晚高峰
    NORMAL;         // 正常时段(闲时)
    
    public boolean isPeakHour() {
        return this == MORNING_PEAK || this == LUNCH_PEAK || this == EVENING_PEAK;
    }
}