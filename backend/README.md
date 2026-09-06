# LocalScope Backend

## Folder Structure

```text
backend/
├── src/main/java/com/localscope/
│   ├── config/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── exception/
│   ├── repository/
│   └── service/
├── src/main/resources/
└── src/test/java/com/localscope/
```

---

## Layers & Responsibilities

### `controller/`
* Handles incoming HTTP requests from the frontend and returns JSON responses.
* Exposes REST endpoints for project discovery, service health, port monitoring, environment timeline, dependency graphs, snapshots, diagnostics, and system metrics.

---

### `service/`
* Executes core business logic and background monitoring tasks.
* **Background Observers:** Continuous scheduled jobs scanning running OS processes, active ports, Docker containers, system resources, and service health checks.
* **Diagnostic & Intelligence:** Evaluates root-cause rules, correlates events over time, discovers project architectures, maps service dependencies, and computes environment snapshot diffs.
* **Event Streaming:** Records environment changes and triggers real-time WebSocket event notifications.

---

### `repository/`
* Handles data persistence and database access on local SQLite via Spring Data JPA.
* Manages CRUD operations and filtered queries for projects, services, dependencies, timeline events, health checks, snapshots, and diagnostic records.

---

### `entity/`
* Defines database table schemas mapped to Java objects.
* Represents domain data models for projects, monitored services, service dependencies, historical events, health checks, environment snapshots, snapshot changes, and diagnostic findings.

---

### `dto/`
* Defines data contracts decoupled from database entities for network communication.
* Contains request payloads, response bodies, and real-time WebSocket notification payloads.

---

### `config/`
* Configures framework behaviors, protocols, and infrastructure settings.
* Manages WebSocket broker configuration, SQLite DataSource and JPA dialect settings, thread pools for scheduled background monitoring tasks, and CORS policies for local frontend development.

---

### `exception/`
* Centralizes error handling and ensures consistent API error responses.
* Intercepts runtime errors, defines custom domain exceptions, and formats structured JSON error messages for the frontend.
