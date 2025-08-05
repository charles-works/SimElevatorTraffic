package com.github.charles.works.simelevatortriffic.domain;

/**
 * 楼层用途枚举
 */
public enum FloorUsage {
    LOBBY,      // 大厅
    PARKING,    // 车库
    OFFICE,     // 办公
    RETAIL,     // 零售
    RESIDENTIAL // 住宅
    ;
    
    /**
     * 获取楼层用途的默认交通模式
     * @return 默认交通模式
     */
    public TrafficPattern getDefaultTrafficPattern() {
        switch (this) {
            case LOBBY:
                // 大厅通常有较多的进入和离开流量
                return new TrafficPattern(0.4, 0.4, 0.2, ElevatorDirection.IDLE, System.currentTimeMillis());
            case PARKING:
                // 车库主要是进出流量
                return new TrafficPattern(0.3, 0.3, 0.4, ElevatorDirection.IDLE, System.currentTimeMillis());
            case OFFICE:
                // 办公楼层在上下班时间有明显的上下行方向
                return new TrafficPattern(0.3, 0.3, 0.4, ElevatorDirection.IDLE, System.currentTimeMillis());
            case RETAIL:
                // 零售楼层主要是进出流量
                return new TrafficPattern(0.35, 0.35, 0.3, ElevatorDirection.IDLE, System.currentTimeMillis());
            case RESIDENTIAL:
                // 住宅楼层在不同时段有不同的流量模式
                return new TrafficPattern(0.25, 0.25, 0.5, ElevatorDirection.IDLE, System.currentTimeMillis());
            default:
                // 默认交通模式
                return TrafficPattern.initialPattern();
        }
    }
}