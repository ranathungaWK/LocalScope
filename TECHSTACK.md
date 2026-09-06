# LocalScope — Technology Stack

---

## 1. Backend Stack

| Component                       | Technology           | Version / Tooling           | Purpose                                                              |
| :------------------------------ | :------------------- | :-------------------------- | :------------------------------------------------------------------- |
| **Language**              | Java                 | 21 (LTS)                    | Type safety, performance, robust cross-platform OS access            |
| **Framework**             | Spring Boot          | 3.x                         | Core framework for DI, REST APIs, scheduling, and configuration      |
| **API Layer**             | Spring Web           | MVC / REST                  | HTTP endpoints for UI queries and project controls                   |
| **Real-Time Push**        | Spring WebSocket     | STOMP                       | Streaming live events, metrics, and diagnostic alerts to the browser |
| **Persistence / ORM**     | Spring Data JPA      | Hibernate                   | Data access layer and entity mappings                                |
| **Background Observers**  | Spring Scheduling    | `@Scheduled` / `@Async` | High-frequency process, port, Docker, and resource polling loops     |
| **Diagnostics / Metrics** | Spring Boot Actuator | Micrometer                  | Self-health monitoring and application metrics                       |
| **Build Tool**            | Apache Maven         | Maven 3.9+                  | Dependency management and packaging                                  |

---

## 2. Frontend Stack

| Component                  | Technology                    | Purpose                                                                                                        |
| :------------------------- | :---------------------------- | :------------------------------------------------------------------------------------------------------------- |
| **Framework**        | Vue 3                         | Reactive Single-Page Application (SPA) using Composition API (`<script setup>`)                              |
| **Language**         | TypeScript                    | Strong typing for API payloads, component props, and stores                                                    |
| **Build Tool**       | Vite                          | Lightning-fast development server with Hot Module Replacement (HMR) and optimized production bundling          |
| **Styling**          | Tailwind CSS                  | Utility-first CSS framework for modern, responsive dashboard UI                                                |
| **State Management** | Pinia                         | Modular reactive stores for active projects, services, timeline events, and alerts                             |
| **Routing**          | Vue Router                    | Client-side routing (`/`, `/services`, `/dependencies`, `/timeline`, `/snapshots`, `/diagnostics`) |
| **Dependency Graph** | Vue Flow (`@vue-flow/core`) | Interactive node-based graph visualizing project architecture and service dependencies                         |
| **Metric Charts**    | Apache ECharts                | High-performance interactive charts for latency, CPU, and memory time-series                                   |
| **WebSocket Client** | `@stomp/stompjs`            | STOMP client connecting to`/ws/environment` for real-time push updates                                       |
| **HTTP Client**      | Axios / Fetch API             | Calling Spring Boot REST endpoints                                                                             |

---

## 3. Persistence & Storage

| Component             | Technology                         | Purpose                                                                                                  |
| :-------------------- | :--------------------------------- | :------------------------------------------------------------------------------------------------------- |
| **Database**    | SQLite                             | Zero-configuration, serverless, file-based SQL database stored locally at`~/.localscope/localscope.db` |
| **JDBC Driver** | `sqlite-jdbc`                    | Standard JDBC driver for SQLite connectivity                                                             |
| **Dialect**     | Hibernate SQLite Community Dialect | Translates JPA queries and entity mappings to SQLite SQL                                                 |

---

## 4. Communication Protocols

* **HTTP / JSON (REST):** Client-to-server request/response communication for data retrieval, settings, and command triggers.
* **WebSocket / STOMP:** Bi-directional real-time messaging channel (`/ws/environment`) broadcasting:
  * `/topic/events`: Immediate environment status changes (e.g. *Redis stopped*).
  * `/topic/diagnostics`: Root-cause alerts emitted by the diagnostic engine.
  * `/topic/services`: Live service health status updates.

---

## 5. CLI & Distribution

| Component                    | Tooling                     | Purpose                                                                                                     |
| :--------------------------- | :-------------------------- | :---------------------------------------------------------------------------------------------------------- |
| **CLI Launcher**       | Bash / PowerShell scripts   | Simple developer command (`localscope start`, `stop`, `status`)                                       |
| **Packaging Strategy** | Standalone Executable / JAR | Bundles compiled Vue static assets into Spring Boot's`resources/static/` for a single-binary distribution |

---

## 6. Core Architectural Constraints

1. **100% Local:** No cloud communication, external telemetry, or SaaS dependencies. All data remains on the developer's computer.
2. **Zero-Configuration:** SQLite automatically creates and manages its database file without requiring a standalone database server (e.g., PostgreSQL/MySQL).
3. **Deterministic First:** Rule-based root-cause correlation engine provides concrete evidence based on time deltas and dependency maps.
