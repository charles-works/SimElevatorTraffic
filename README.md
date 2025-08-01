# SimElevatorTraffic - Elevator Traffic Simulation and Analysis Software

[中文版 README](README_CN.md)

## Project Overview

> **Note**: This project has been migrated from the Maven build system to Gradle 8.14 to provide better build performance and a more modern build experience.

SimElevatorTraffic is a professional elevator traffic simulation and analysis tool designed to address key issues in modern building elevator system design and optimization. Developed based on the latest research materials and best practices in elevator engineering, this project has the following features:

- Supports precise simulation of 4 elevator control methods: Single Control, Parallel Control, Group Control, and Destination Dispatch
- Simulates traffic characteristics of different building types (office buildings, shopping malls, residential buildings, mixed-use)
- Models traffic patterns during different time periods such as morning rush, lunch rush, evening rush, and off-peak hours
- Supports passenger batch arrival models that conform to the "non-homogeneous Poisson process" characteristic
- Implements OD matrix estimation methods (LP, BILS, CP algorithms)
- Provides detailed performance indicator analysis, including waiting time, transportation time, system utilization, etc.
- Supports simulation of emergency evacuation scenarios
- Provides RESTful API interfaces for integration with other building management systems

## Technology Stack

- **Core Language**: Java 21
- **Build Tool**: Gradle 8.14
- **Web Framework**: Javalin
- **Database**: PostgreSQL
- **JSON Processing**: Jackson
- **PDF Generation**: iText 7
- **Constraint Programming**: Choco Solver
- **Mathematical Computing**: Apache Commons Math
- **Testing Frameworks**: JUnit 5, Mockito

## System Architecture

```
SimElevatorTraffic/
├── src/
│   └── main/
│       └── java/
│           └── com/github/charles/works/simelevatortriffic/
│               ├── Main.java                 # Application entry point
│               ├── api/                      # REST API layer
│               ├── config/                   # Configuration management
│               ├── domain/                   # Domain model
│               ├── service/                  # Business logic layer
│               ├── simulation/               # Simulation engine
├── build.gradle                              # Gradle build configuration
├── settings.gradle                           # Gradle settings
├── gradlew                                   # Gradle Wrapper script
└── README.md                                 # Project documentation
```

## Quick Start

### Environment Requirements

- Java 21 JDK
- PostgreSQL 13+
- Gradle 8.14+

### Building the Project

```bash
# Clone the project
git clone <repository-url>
cd SimElevatorTraffic

# Build the project
./gradlew build

# Run the application
./gradlew run
```

### API Usage Example

```bash
# Execute simulation
curl -X POST http://localhost:7000/api/v1/simulate \
  -H "Content-Type: application/json" \
  -d '{
    "buildingConfig": {
      "name": "Office Building",
      "totalFloors": 30,
      "floorHeight": 4.0,
      "buildingType": "OFFICE"
    },
    "elevatorGroups": [
      {
        "controlType": "GROUP_CONTROL",
        "elevators": [
          {
            "capacity": 16,
            "ratedSpeed": 3.0,
            "acceleration": 1.0,
            "deceleration": 1.0,
            "doorWidth": 1.0,
            "standbyFloor": 1
          }
        ]
      }
    ],
    "simulationConfig": {
      "duration": 3600,
      "startTime": "08:00",
      "endTime": "09:00"
    }
  }'
```

## Core Features

### 1. Elevator Control Strategy Simulation

Supports four elevator control methods:
- **Single Control**: Each elevator independently responds to its own call signals
- **Parallel Control**: Two elevators share calls, using a minimum waiting time algorithm
- **Group Control**: Multiple elevators work together, considering overall performance optimization
- **Destination Dispatch**: Passengers first input their destination floor, and the system optimizes allocation

### 2. Passenger Flow Modeling

Implements high-fidelity passenger flow models based on research materials:
- **Batch Arrival Model**: Implements "non-homogeneous Poisson process"
- **Traffic Pattern Modeling**: Supports inbound, outbound, and inter-floor traffic components
- **OD Matrix Estimation**: Implements LP, BILS, and CP algorithms

### 3. Performance Analysis

Provides comprehensive performance indicator analysis:
- Waiting time analysis (average, maximum, distribution)
- Transportation time analysis (average, maximum, distribution)
- System utilization analysis
- 5-minute handling capacity (HC)
- Elevator energy consumption estimation

### 4. Visualization and Reporting

- Real-time simulation animation display
- Interactive data analysis interface
- Professional PDF report generation
- Elevator utilization heatmap

## Development Guide

### Domain Model

Core domain objects include:
- `Building`: Building model
- `Floor`: Floor information
- `ElevatorGroup`: Elevator group
- `Elevator`: Elevator instance
- `Passenger`: Passenger object
- `ODMetricMatrix`: OD matrix

### Service Layer

Main service interfaces:
- `ODEstimationService`: OD matrix estimation
- `PassengerFlowService`: Passenger flow generation
- `ElevatorControlService`: Elevator control strategy
- `SimulationService`: Simulation coordination management

### Simulation Engine

`ElevatorSimulationScheduler` is the core simulation scheduler, using discrete event simulation methods:
- Event-driven architecture
- Real-time performance optimization
- Support for large-scale simulation

## Configuration

Application configuration is managed through the `ApplicationConfig` class, with main configuration items including:

### Database Configuration
```properties
# Database connection URL
database.url=jdbc:postgresql://localhost:5432/sim_elevator
# Database username
database.username=sim_user
# Database password
database.password=sim_password
```

### Simulation Configuration
```properties
# Maximum concurrent simulations
simulation.maxConcurrentSimulations=10
# Default simulation duration (seconds)
simulation.defaultSimulationDuration=3600
# Default time slice (seconds)
simulation.defaultTimeSlice=5
```

### API Configuration
```properties
# API service port
api.port=7000
# Request timeout (milliseconds)
api.requestTimeoutMs=30000
```

## Testing Strategy

### Unit Testing

Uses JUnit 5 and Mockito for unit testing, covering core algorithms and business logic.

### Integration Testing

Validates integration with databases and external services.

### Performance Testing

Uses JMH for performance benchmark testing to ensure real-time requirements are met.

## Deployment Instructions

### Production Environment Deployment

1. Build the project:
```bash
./gradlew build
```

2. Configure the database:
```sql
CREATE DATABASE sim_elevator;
CREATE USER sim_user WITH PASSWORD 'sim_password';
GRANT ALL PRIVILEGES ON DATABASE sim_elevator TO sim_user;
```

3. Run the application:
```bash
java -jar build/libs/SimElevatorTriffic-1.0-SNAPSHOT.jar
```

### Docker Deployment

Dockerfile is provided for containerized deployment.

## Contribution Guidelines

Issues and Pull Requests are welcome to improve the project.

## License

This project is licensed under the Apache 2.0 License.

## Contact

For any questions, please contact the project maintainer.