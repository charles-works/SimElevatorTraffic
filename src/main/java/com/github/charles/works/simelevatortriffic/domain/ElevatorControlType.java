package com.github.charles.works.simelevatortriffic.domain;

/**
 * 电梯控制类型枚举
 * 实现PDF中描述的单控、并联、群控、目的选层四种控制方式
 */
public enum ElevatorControlType {
    SINGLE_CONTROL,    // 单控
    PARALLEL_CONTROL,  // 并联(2台)
    GROUP_CONTROL,     // 群控(3+台)
    DESTINATION_CONTROL // 目的选层
}