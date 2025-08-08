# SimElevatorTraffic - 电梯交通量仿真分析软件

[English Version README](README.md)

## 项目概述

> **注意**: 本项目已从Maven构建系统迁移到Gradle 8.14，以提供更好的构建性能和更现代的构建体验。

SimElevatorTraffic是一个专业的电梯交通量仿真分析工具，旨在解决现代建筑电梯系统设计与优化中的关键问题。本项目基于最新的研究资料和电梯工程最佳实践开发，具有以下特点：

- 支持4种电梯控制方式的精确仿真：单控、并联、群控、目的选层
- 模拟不同建筑类型(办公楼、商场、住宅、混合用途)的交通特性
- 建模早高峰、午餐高峰、晚高峰和闲时等不同时段的交通模式
- 支持乘客批次到达模型，符合"时间非齐次泊松过程"特性
- 实现OD矩阵估计方法(LP、BILS、CP算法)
- 提供详细的性能指标分析，包括等待时间、运输时间、系统利用率等
- 支持紧急疏散场景的仿真
- 提供RESTful API接口，可与其他建筑管理系统集成

## 技术栈

- **核心语言**: Java 21
- **构建工具**: Gradle 8.14
- **Web框架**: Javalin
- **数据库**: PostgreSQL
- **JSON处理**: Jackson
- **PDF生成**: iText 7
- **约束规划**: Choco Solver
- **数学计算**: Apache Commons Math
- **测试框架**: JUnit 5, Mockito

## 系统架构

```
SimElevatorTraffic/
├── src/
│   └── main/
│       └── java/
│           └── com/github/charles/works/simelevatortriffic/
│               ├── Main.java                 # 应用入口
│               ├── api/                      # REST API层
│               ├── config/                   # 配置管理
│               ├── domain/                   # 领域模型
│               ├── service/                  # 业务逻辑层
│               └── simulation/               # 仿真引擎
├── build.gradle                              # Gradle构建配置
├── settings.gradle                           # Gradle设置
├── gradlew                                   # Gradle Wrapper脚本
└── README.md                                 # 项目说明文档
```

## 快速开始

### 环境要求

- Java 21 JDK
- PostgreSQL 13+
- Gradle 8.14+

### 构建项目

```bash
# 克隆项目
git clone <repository-url>
cd SimElevatorTraffic

# 构建项目
./gradlew build

# 运行应用
./gradlew run
```

### API使用示例

```bash
# 使用统一楼层配置执行仿真
curl -X POST http://localhost:7000/api/v1/simulate \
  -H "Content-Type: application/json" \
  -d '{
    "buildingConfig": {
      "id": "1",
      "name": "办公大楼",
      "floors": 30,
      "floorHeight": 4.0,
      "type": "OFFICE"
    },
    "elevatorGroups": [
      {
        "id": "1",
        "controlType": "GROUP_CONTROL",
        "servedFloors": [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30],
        "elevators": [
          {
            "id": "1",
            "capacity": 16,
            "ratedSpeed": 3.0,
            "acceleration": 1.0,
            "deceleration": 1.0,
            "doorWidth": 1.0,
            "standbyFloor": 1,
            "serviceFloors": [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]
          },
          {
            "id": "2",
            "capacity": 13,
            "ratedSpeed": 3.0,
            "acceleration": 1.0,
            "deceleration": 1.0,
            "doorWidth": 1.0,
            "standbyFloor": 1,
            "serviceFloors": [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]
          },
          {
            "id": "3",
            "capacity": 13,
            "ratedSpeed": 3.0,
            "acceleration": 1.0,
            "deceleration": 1.0,
            "doorWidth": 1.0,
            "standbyFloor": 1,
            "serviceFloors": [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]
          }
        ]
      }
    ],
    "simulationConfig": {
      "duration": 3600
    }
  }'

# 使用每层不同配置执行仿真
curl -X POST http://localhost:7000/api/v1/simulate \
  -H "Content-Type: application/json" \
  -d '{
    "buildingConfig": {
      "id": "2",
      "name": "综合用途建筑",
      "floors": 20,
      "floorHeight": 4.0,
      "type": "MIXED",
      "floorConfigs": [
        {
          "floorNumber": 1,
          "usage": "LOBBY",
          "population": 50,
          "arrivalRate": 2.0,
          "floorHeight": 5.0
        },
        {
          "floorNumber": 2,
          "usage": "RETAIL",
          "population": 100,
          "arrivalRate": 3.0,
          "floorHeight": 4.5
        },
        {
          "floorNumber": 3,
          "usage": "OFFICE",
          "population": 200,
          "arrivalRate": 5.0,
          "floorHeight": 4.0
        },
        {
          "floorNumber": 4,
          "usage": "OFFICE",
          "population": 180,
          "arrivalRate": 4.5,
          "floorHeight": 4.0
        }
      ]
    },
    "elevatorGroups": [
      {
        "id": "1",
        "controlType": "GROUP_CONTROL",
        "servedFloors": [1,2,3,4],
        "elevators": [
          {
            "id": "1",
            "capacity": 16,
            "ratedSpeed": 3.0,
            "acceleration": 1.0,
            "deceleration": 1.0,
            "doorWidth": 1.0,
            "standbyFloor": 1,
            "serviceFloors": [1,2,3,4]
          },
          {
            "id": "2",
            "capacity": 13,
            "ratedSpeed": 3.0,
            "acceleration": 1.0,
            "deceleration": 1.0,
            "doorWidth": 1.0,
            "standbyFloor": 1,
            "serviceFloors": [1,2,3,4]
          }
        ]
      }
    ],
    "simulationConfig": {
      "duration": 3600
    }
  }'
```

## 核心功能

### 1. 电梯控制策略仿真

支持四种电梯控制方式：
- **单控**(SINGLE_CONTROL): 每台电梯独立响应本梯召唤信号
- **并联**(PARALLEL_CONTROL): 两台电梯共享召唤，采用最小等待时间算法
- **群控**(GROUP_CONTROL): 多台电梯协同工作，考虑整体性能优化
- **目的选层**(DESTINATION_CONTROL): 乘客先输入目标楼层，系统进行优化分配

### 2. 乘客流量建模

基于研究资料实现高保真度的乘客流量模型：
- **批次到达模型**: 实现"时间非齐次泊松过程"
- **交通模式建模**: 支持进楼、出楼、楼层间三种交通成分
- **OD矩阵估计**: 实现LP、BILS、CP三种算法

### 3. 性能分析

提供全面的性能指标分析：
- 等待时间分析(平均、最大、分布)
- 运输时间分析(平均、最大、分布)
- 系统利用率分析
- 5分钟输送能力(HC)
- 电梯能耗估算

### 4. 可视化与报告

- 实时仿真动画展示
- 交互式数据分析界面
- 专业PDF报告生成
- 电梯利用率热力图

## 开发指南

### 领域模型

核心领域对象包括：
- `Building`: 建筑模型
- `Floor`: 楼层信息
- `ElevatorGroup`: 电梯组
- `Elevator`: 电梯实例
- `Passenger`: 乘客对象
- `ODMetricMatrix`: OD矩阵

### 服务层

主要服务接口：
- `ODEstimationService`: OD矩阵估计
- `PassengerFlowService`: 乘客流量生成
- `ElevatorControlService`: 电梯控制策略
- `SimulationService`: 仿真协调管理

### 仿真引擎

`ElevatorSimulationScheduler`是核心仿真调度器，采用离散事件仿真方法：
- 事件驱动架构
- 实时性能优化
- 支持大规模仿真

## 配置说明

应用配置通过`ApplicationConfig`类管理，主要配置项包括：

### 数据库配置
```properties
# 数据库连接URL
database.url=jdbc:postgresql://localhost:5432/sim_elevator
# 数据库用户名
database.username=sim_user
# 数据库密码
database.password=sim_password
```

### 仿真配置
```properties
# 最大并发仿真数
simulation.maxConcurrentSimulations=10
# 默认仿真时长(秒)
simulation.defaultSimulationDuration=3600
# 默认时间切片(秒)
simulation.defaultTimeSlice=5
```

### API配置
```properties
# API服务端口
api.port=7000
# 请求超时时间(毫秒)
api.requestTimeoutMs=30000
```

## 测试策略

### 单元测试

使用JUnit 5和Mockito进行单元测试，覆盖核心算法和业务逻辑。

### 集成测试

验证与数据库、外部服务的集成。

### 性能测试

使用JMH进行性能基准测试，确保满足实时性要求。

## 部署说明

### 生产环境部署

1. 构建项目：
```bash
./gradlew build
```

2. 配置数据库：
```sql
CREATE DATABASE sim_elevator;
CREATE USER sim_user WITH PASSWORD 'sim_password';
GRANT ALL PRIVILEGES ON DATABASE sim_elevator TO sim_user;
```

3. 运行应用：
```bash
java -jar build/libs/SimElevatorTriffic-1.0-SNAPSHOT.jar
```

### Docker部署

提供Dockerfile支持容器化部署。

## 贡献指南

欢迎提交Issue和Pull Request来改进项目。

## 许可证

本项目采用Apache 2.0许可证。

## 联系方式

如有问题，请联系项目维护者。