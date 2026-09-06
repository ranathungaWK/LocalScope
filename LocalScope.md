# LocalScope

**(Agent Context)**

## 1. Project Overview

LocalScope is a locally installed developer tool with a web-based UI. It runs a local background agent and a Spring Boot backend on the developer's machine, while developers interact with the system through a browser.

LocalScope continuously observes a software project's local development environment and correlates environment changes with application behavior.

### Example

A developer has the following local services:

```text
React              :3000
Spring Boot        :8080
PostgreSQL         :5432
Redis              :6379
Python ML API      :8000
```

If Redis crashes, LocalScope can identify the sequence:

```text
Redis :6379
     |
     v
SERVICE DOWN
     |
     v
Spring Boot dependency affected
     |
     v
/orders API starts returning 500
     |
     v
Failure detected
```

Instead of only reporting that Redis is down, LocalScope can report:

> **Possible Root Cause:** Redis became unavailable 3.8 seconds before the increase in `/orders` failures.

That temporal correlation is the central idea of the project.

## 2. Objectives

### Primary Objective

Build a locally installed developer tool that provides real-time visibility, monitoring, historical analysis, and basic root-cause diagnosis of a software project's local development environment.

### Specific Objectives

1. Monitor local development services:
   - Processes
   - Ports
   - Docker containers
   - Databases
   - APIs
2. Understand project architecture:
   - Identify services
   - Identify dependencies
   - Build a service dependency graph
3. Track environment changes:
   - Services starting and stopping
   - Port changes
   - Configuration changes
   - Container changes
   - Resource changes
4. Correlate environment changes with application failures.
5. Provide real-time monitoring through a web UI.
6. Maintain historical environment information.
7. Compare working and broken environments.
8. Provide evidence-based diagnostics.
9. Keep the entire system local with no cloud dependency for the MVP.

## 3. Key Features

### Feature 1: Local Environment Discovery

Automatically discover information about the developer's machine, including running processes and ports.

```text
Running Processes
-----------------
java
node
python
postgres
redis
docker
```

```text
Ports
-----
3000 -> Node
8080 -> Java
5432 -> PostgreSQL
6379 -> Redis
8000 -> Python
```

### Feature 2: Project Detection

Identify a specific development project instead of monitoring the entire computer blindly.

Example project structure:

```text
MyShop/
|
|-- frontend/
|   `-- package.json
|-- backend/
|   `-- pom.xml
|-- ml-service/
|   `-- requirements.txt
`-- docker-compose.yml
```

Detected architecture:

```text
Project: MyShop

Frontend
   |
   v
Spring Boot Backend
   |
   +--> PostgreSQL
   +--> Redis
   `--> Python ML Service
```

### Feature 3: Service Monitoring

Each service receives a health state:

- `RUNNING`
- `WARNING`
- `DOWN`
- `UNKNOWN`

| Service     | Port | Status  | Response |
| ----------- | ---: | ------- | -------: |
| React       | 3000 | Running |    12 ms |
| Spring Boot | 8080 | Running |    25 ms |
| PostgreSQL  | 5432 | Running |     8 ms |
| Redis       | 6379 | Down    |        - |
| ML API      | 8000 | Warning |   820 ms |

### Feature 4: Port Monitoring

Monitor local ports and detect conflicts.

```text
Port 8080

Expected:
Spring Boot

Current:
Another Java process

Port conflict detected
```

### Feature 5: Dependency Graph

Display the project's local architecture visually. The graph should eventually be generated from actual project configuration and observations rather than being manually created.

```text
             +-------------+
             |    React    |
             |    :3000    |
             +------+------+
                    |
                    v
             +-------------+
             | Spring Boot |
             |    :8080    |
             +---+-----+---+
                 |     |
          +------v-+ +-v---------+
          | Redis  | | PostgreSQL|
          | :6379  | |  :5432    |
          +--------+ +-----------+
```

### Feature 6: Real-Time Environment Timeline

Record important events chronologically.

```text
10:32:01  PostgreSQL started
10:32:04  Spring Boot started
10:32:06  Redis started
10:34:18  Redis stopped
10:34:21  /orders latency increased
10:34:23  /orders returned 500
10:34:25  Redis restarted
10:34:27  API recovered
```

### Feature 7: Root-Cause Diagnostic Engine

The initial diagnostic engine should be rule-based rather than AI-based.

Example rule:

```text
IF a dependency becomes unavailable
AND the dependent service starts failing
AND the failure occurs shortly after the dependency failure
THEN create a diagnostic event
```

Example result:

```text
POSSIBLE ROOT CAUSE

Redis became unavailable 3.8 seconds
before backend failures started.

Affected service:
Spring Boot

Affected endpoint:
/orders

Confidence:
HIGH
```

Initial diagnostic rules may include:

- Dependency failure
- Port conflict
- Unusual latency
- Service crash
- Resource saturation
- Repeated restart

### Feature 8: Environment Snapshots

Capture a known working environment.

```text
Snapshot #21

Project: MyShop

Services
- React
- Spring Boot
- PostgreSQL
- Redis
- ML API

Ports
3000
8080
5432
6379
8000

Docker
3 containers

Environment
Java 21
Node 22
Python 3.12
```

### Feature 9: Environment Diff

Compare a working environment with the current environment.

```text
Environment Difference

Java
21 -> 23

Redis
Running -> Stopped

PostgreSQL
5432 -> 5433

Docker container
redis:7 -> redis:8

Environment variable
DATABASE_URL -> changed
```

### Feature 10: API Activity Monitoring

For applications where LocalScope can observe HTTP traffic, display request counts, errors, and latency.

| Endpoint          | Requests | Errors | Average Latency |
| ----------------- | -------: | -----: | --------------: |
| `GET /users`    |      245 |      2 |           42 ms |
| `GET /orders`   |      391 |     28 |          180 ms |
| `POST /login`   |      102 |      0 |           35 ms |
| `GET /products` |      812 |      0 |           21 ms |

LocalScope can also identify unusual behavior, such as an endpoint increasing from a normal latency of 40-60 ms to 450 ms.

### Feature 11: Resource Monitoring

Monitor:

- CPU
- Memory
- Disk
- Network
- Process usage
- Container resources

Example:

```text
Spring Boot

CPU       71%
Memory    1.8 GB
Network   12 MB/s
Threads   86
```

### Feature 12: Real-Time Updates

The UI should update without requiring constant refreshing.

```text
Local Agent
     |
     v
Spring Boot
     |
     | WebSocket
     v
Browser
```

## 4. System Architecture

LocalScope is a local software product that happens to have a web UI, rather than a traditional deployed web application.

```text
                  USER'S COMPUTER
+----------------------------------------------------+
|                                                    |
|                   LocalScope                       |
|                                                    |
|  +-----------------------+                         |
|  |     Local Agent       |                         |
|  |                       |                         |
|  | - Process discovery   |                         |
|  | - Port monitoring     |                         |
|  | - Docker monitoring   |                         |
|  | - Resource monitoring |                         |
|  | - Network monitoring  |                         |
|  | - Service detection   |                         |
|  +-----------+-----------+                         |
|              |                                     |
|              v                                     |
|  +------------------------------------------+      |
|  |             Spring Boot                  |      |
|  |                                          |      |
|  | REST API                                |      |
|  | WebSocket                               |      |
|  | Diagnostic Engine                       |      |
|  | Event Processor                         |      |
|  | Project Manager                         |      |
|  | Snapshot / Diff Engine                  |      |
|  +-----------------+------------------------+      |
|                    |                               |
|                    v                               |
|             +--------------+                      |
|             |    SQLite    |                      |
|             |   Local DB   |                      |
|             +--------------+                      |
|                                                    |
+-----------------------+----------------------------+
                        |
                        | HTTP / WebSocket
                        v
               +------------------+
               |      Browser     |
               |   LocalScope UI  |
               +------------------+
```

## 5. Component Responsibilities

### LocalScope Agent

The agent accesses operating-system information and sends structured observations to the Spring Boot server.

It handles:

- Process information
- Port information
- Docker
- Network
- CPU
- Memory
- Disk
- Environment variables
- Service status

Example event:

```json
{
  "event": "SERVICE_STATUS_CHANGED",
  "service": "redis",
  "previousStatus": "RUNNING",
  "currentStatus": "STOPPED",
  "timestamp": "2026-09-06T10:34:18"
}
```

### Spring Boot Backend

Spring Boot is the core of LocalScope.

Responsibilities include:

- REST API
- WebSocket communication
- Diagnostic engine
- Event processing
- Project management
- Snapshot and diff processing

Suggested REST resources:

```text
/projects
/services
/ports
/events
/dependencies
/snapshots
/diagnostics
/metrics
```

Suggested WebSocket endpoint:

```text
/ws/environment
```

Event processing flow:

```text
Redis stopped
     |
     v
Event Processor
     |
     v
Store event
     |
     v
Check diagnostic rules
     |
     v
Generate diagnosis
```

## 6. Frontend

Recommended frontend stack:

- Vue 3
- TypeScript
- Vite
- Tailwind CSS
- Vue Router
- Pinia
- ECharts
- Vue Flow
- WebSocket

Vite provides a fast development server, fast HMR, simple project setup, modern TypeScript support, and straightforward production builds.

### Frontend Structure

```text
src/
|
|-- components/
|   |-- ServiceCard.vue
|   |-- HealthStatus.vue
|   |-- EventTimeline.vue
|   |-- DependencyGraph.vue
|   |-- MetricChart.vue
|   `-- DiagnosticCard.vue
|
|-- views/
|   |-- Dashboard.vue
|   |-- Environment.vue
|   |-- Services.vue
|   |-- Dependencies.vue
|   |-- Timeline.vue
|   |-- Snapshots.vue
|   `-- Diagnostics.vue
|
|-- stores/
|   |-- project.ts
|   |-- services.ts
|   `-- environment.ts
|
|-- services/
|   |-- api.ts
|   `-- websocket.ts
|
`-- router/
    `-- index.ts
```

## 7. Recommended Technology Stack

| Layer                   | Technology                         |
| ----------------------- | ---------------------------------- |
| Frontend                | Vue 3 + TypeScript                 |
| Build tool              | Vite                               |
| UI                      | Tailwind CSS                       |
| State management        | Pinia                              |
| Charts                  | ECharts                            |
| Dependency graph        | Vue Flow                           |
| Backend                 | Spring Boot                        |
| API                     | Spring Web                         |
| Real-time communication | Spring WebSocket                   |
| ORM                     | Spring Data JPA                    |
| Validation              | Jakarta Bean Validation            |
| Monitoring              | Spring Boot Actuator               |
| Database                | SQLite                             |
| Agent                   | Java                               |
| Container monitoring    | Docker API                         |
| Packaging               | Native installer or executable     |
| Testing                 | JUnit, Mockito, and frontend tests |
| Build                   | Maven                              |
| CI/CD                   | GitHub Actions                     |
| Version control         | Git/GitHub                         |

## 8. Database Design

### Projects

| Column         | Description        |
| -------------- | ------------------ |
| `id`         | Project identifier |
| `name`       | Project name       |
| `path`       | Project path       |
| `created_at` | Creation timestamp |

### Services

| Column         | Description        |
| -------------- | ------------------ |
| `id`         | Service identifier |
| `project_id` | Owning project     |
| `name`       | Service name       |
| `type`       | Service type       |
| `host`       | Service host       |
| `port`       | Service port       |
| `pid`        | Process identifier |
| `status`     | Current status     |
| `started_at` | Start timestamp    |

### Dependencies

| Column                | Description                  |
| --------------------- | ---------------------------- |
| `id`                | Dependency identifier        |
| `source_service_id` | Dependent service            |
| `target_service_id` | Dependency target            |
| `dependency_type`   | Dependency relationship type |

### Events

| Column         | Description           |
| -------------- | --------------------- |
| `id`         | Event identifier      |
| `service_id` | Related service       |
| `event_type` | Event type            |
| `timestamp`  | Event timestamp       |
| `metadata`   | Additional event data |

### Health Checks

| Column            | Description             |
| ----------------- | ----------------------- |
| `id`            | Health check identifier |
| `service_id`    | Related service         |
| `status`        | Health status           |
| `response_time` | Response time           |
| `timestamp`     | Check timestamp         |

### Environment Snapshots

| Column         | Description         |
| -------------- | ------------------- |
| `id`         | Snapshot identifier |
| `project_id` | Related project     |
| `created_at` | Creation timestamp  |
| `metadata`   | Snapshot metadata   |

### Environment Changes

| Column          | Description       |
| --------------- | ----------------- |
| `id`          | Change identifier |
| `snapshot_id` | Related snapshot  |
| `property`    | Changed property  |
| `old_value`   | Previous value    |
| `new_value`   | Current value     |

### Diagnostics

| Column         | Description           |
| -------------- | --------------------- |
| `id`         | Diagnostic identifier |
| `project_id` | Related project       |
| `severity`   | Diagnostic severity   |
| `message`    | Diagnostic message    |
| `confidence` | Confidence level      |
| `created_at` | Creation timestamp    |

## 9. Dashboard Concept

The main dashboard should show:

- Current project
- Healthy, warning, and down service counts
- Dependency graph
- Active diagnostics
- Recent events
- Service and resource metrics

Example layout:

```text
+------------------------------------------------------+
| LocalScope                         Project: MyShop   |
+------------------------------------------------------+
|                                                      |
| SERVICES                                             |
|                                                      |
|  4 Healthy       1 Warning       1 Down              |
|                                                      |
+------------------------------------------------------+
|                                                      |
| DEPENDENCY GRAPH                                     |
|                                                      |
|          React                                       |
|            |                                         |
|            v                                         |
|       Spring Boot                                    |
|        |       |                                     |
|        v       v                                     |
|      Redis   PostgreSQL                              |
|       DOWN       OK                                  |
|                                                      |
+------------------------------------------------------+
|                                                      |
| DIAGNOSTIC                                           |
|                                                      |
| Redis became unavailable 3.8 seconds before         |
| backend failures increased.                         |
|                                                      |
| Confidence: HIGH                                     |
|                                                      |
+------------------------------------------------------+
|                                                      |
| RECENT EVENTS                                        |
|                                                      |
| 10:34:18 Redis stopped                               |
| 10:34:21 API latency increased                       |
| 10:34:23 /orders returned 500                        |
|                                                      |
+------------------------------------------------------+
```

## 10. Development Phases

### Phase 1: Foundation

- Spring Boot
- Vue and Vite
- SQLite
- REST API
- Basic dashboard

### Phase 2: Local Agent

- Process discovery
- Port discovery
- CPU monitoring
- Memory monitoring
- Service status

### Phase 3: Real-Time Monitoring

- Event collection
- Event storage
- WebSocket
- Real-time UI updates
- Timeline

### Phase 4: Project Intelligence

- Project detection
- Service detection
- Dependency detection
- Dependency graph

### Phase 5: Diagnostics

- Rule engine
- Dependency failure detection
- Port conflict detection
- Service crash detection
- Latency anomaly detection
- Root-cause correlation

### Phase 6: Environment Intelligence

- Snapshots
- Environment diff
- Historical events
- Resource correlation

### Phase 7: Packaging

Package the project as a local software product:

```text
localscope
     |
     v
Installer
     |
     v
LocalScope Agent
     |
     v
Spring Boot Server
     |
     v
SQLite
     |
     v
Browser UI
```

## 11. Installation Concept

The final product should feel like a local developer tool:

```text
localscope start
```

Example startup information:

```text
+-------------------------------------+
|           LocalScope                |
|                                     |
|  Server       Running               |
|  Agent        Running               |
|  Database     Connected             |
|                                     |
|  Web UI                              |
|  http://localhost:9090              |
+-------------------------------------+
```

The developer opens `http://localhost:9090` to access the dashboard.

## 12. Future AI Layer

AI should not be part of the initial MVP. LocalScope should first collect reliable evidence through observations, events, metrics, and dependency relationships.

```text
Local Machine
      |
      v
Observations
      |
      v
Events
      |
      v
Metrics
      |
      v
Dependency Graph
      |
      v
Deterministic Diagnostic Engine
      |
      v
Evidence
      |
      v
AI Explanation
```

The AI layer could eventually explain evidence in human-readable language:

> The most likely cause is Redis becoming unavailable. The timing and dependency relationship strongly correlate with the increase in `/orders` failures.

The guiding philosophy is:

> **AI explains the evidence. LocalScope creates the evidence.**

## 13. What Makes LocalScope Different?

Process monitoring, port scanners, APM tools, and Docker dashboards already exist individually. LocalScope combines them around the local development environment and correlates changes with later application behavior.

The key idea is:

> **Don't just tell the developer what is currently wrong. Tell them what changed and what happened afterward.**

Example timeline:

```text
TIME -------------------------------------------------->

Redis running
      |
      v
Redis stopped
      |
      | 3.8 seconds
      v
API latency increased
      |
      | 2 seconds
      v
500 errors increased
      |
      v
Diagnosis
```

## 14. Final Project Definition

### Name

**LocalScope**

### Tagline & Core Mission

> **"Understand what changed when your local application breaks."**
> *(Continuously monitors local processes, ports, containers, and resources to temporally correlate environment changes with application failures for deterministic root-cause diagnosis.)*

### One-Line Description

> LocalScope is a 100% local developer observability tool that continuously observes, models, and diagnoses the state of a software project's local development environment through a web-based interface.

### Core Technologies

```text
Vue 3 + Vite + TypeScript
             |
             v
      Spring Boot
             |
      +------+------+
      v             v
   SQLite       WebSocket
      |             |
      +------+------+
             v
       Local Agent
             |
    +--------+--------+
    v        v        v
 Processes  Ports   Docker
```

### MVP Features

- Local software installation
- Local web UI
- Project detection
- Process discovery
- Port monitoring
- Service health monitoring
- Real-time updates
- Event timeline
- Basic diagnostics

### Advanced Features

- Dependency graph
- Docker monitoring
- API activity
- Resource monitoring
- Environment snapshots
- Environment diff
- Root-cause correlation
- Historical analysis

### Future Features

- AI explanations
- Automatic dependency discovery
- Request correlation
- Environment reproducibility
- Advanced root-cause scoring
