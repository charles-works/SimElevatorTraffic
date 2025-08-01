package com.github.charles.works.simelevatortriffic.service;

import com.github.charles.works.simelevatortriffic.domain.*;
import com.github.charles.works.simelevatortriffic.service.ServiceHelpers.*;

import java.util.List;

/**
 * 电梯控制服务，实现不同控制策略的核心算法
 * 根据PDF中"电梯组控制"的研究实现
 */
public interface ElevatorControlService {
    
    /**
     * 处理电梯召唤请求
     * 
     * @param group 电梯组
     * @param request 召唤请求
     * @return 分配结果
     */
    ServiceHelpers.AssignmentResult handleCallRequest(ElevatorGroup group, ServiceHelpers.CallRequest request);
    
    /**
     * 计算电梯响应时间
     * 
     * @param elevator 电梯
     * @param floor 楼层
     * @param direction 运行方向
     * @return 预计到达时间(秒)
     */
    double calculateResponseTime(Elevator elevator, int floor, ElevatorDirection direction);
    
    /**
     * 更新电梯状态
     * 
     * @param elevator 电梯
     * @param currentTime 当前仿真时间
     * @return 更新后的状态
     */
    ElevatorStatus updateElevatorStatus(Elevator elevator, long currentTime);
    
    /**
     * 识别当前交通模式
     * 
     * @param group 电梯组
     * @param recentEvents 最近事件
     * @return 识别的交通模式
     */
    TrafficPattern recognizeTrafficPattern(ElevatorGroup group, List<SimulationEvent> recentEvents);
}