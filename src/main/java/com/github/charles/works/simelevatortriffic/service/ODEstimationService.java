package com.github.charles.works.simelevatortriffic.service;

import com.github.charles.works.simelevatortriffic.domain.*;
import com.github.charles.works.simelevatortriffic.service.ServiceHelpers.*;

import java.util.List;

/**
 * OD矩阵估计服务，实现PDF中描述的LP、BILS、CP算法
 * "电梯行程OD矩阵估计问题可以表述为网络流问题"
 */
public interface ODEstimationService {
    
    /**
     * 估算OD矩阵
     * 
     * @param building 建筑对象
     * @param profiles 批次到达数据
     * @return OD矩阵
     */
    ODMetricMatrix estimateODMatrix(Building building, List<BatchArrivalProfile> profiles);
    
    /**
     * 使用线性规划(LP)方法估计OD矩阵
     * 
     * @param elevatorTrips 电梯行程数据
     * @param building 建筑信息
     * @return 估计的OD矩阵
     */
    ODMetricMatrix estimateWithLP(List<ServiceHelpers.ElevatorTrip> elevatorTrips, Building building);
    
    /**
     * 使用BILS(带界约束的整数最小二乘)方法估计OD矩阵
     * 
     * @param elevatorTrips 电梯行程数据
     * @param building 建筑信息
     * @param withRandomization 是否启用随机化搜索
     * @return 估计的OD矩阵
     */
    ODMetricMatrix estimateWithBILS(List<ServiceHelpers.ElevatorTrip> elevatorTrips, Building building, boolean withRandomization);
    
    /**
     * 使用CP(约束规划)方法估计OD矩阵
     * 
     * @param elevatorTrips 电梯行程数据
     * @param building 建筑信息
     * @return 估计的OD矩阵
     */
    ODMetricMatrix estimateWithCP(List<ServiceHelpers.ElevatorTrip> elevatorTrips, Building building);
    
    /**
     * 验证OD矩阵的一致性
     * 
     * @param matrix 待验证的OD矩阵
     * @param building 建筑信息
     * @return 验证结果
     */
    ServiceHelpers.ValidationResult validateODMatrix(ODMetricMatrix matrix, Building building);
}