# LocalScope Frontend (`frontend/`)

The **LocalScope Frontend** is a modern Single-Page Application (SPA) built with **Vue 3, TypeScript, Vite, Tailwind CSS, Pinia, ECharts, and Vue Flow**. It connects to the Spring Boot backend via REST APIs and a WebSocket stream to deliver a real-time, responsive local development dashboard.

---

## High-Level Folder Structure & File Responsibilities

```text
frontend/
├── src/
│   ├── components/      # Reusable visual widgets & UI building blocks
│   ├── views/           # Full-page route views (Dashboard, Timeline, etc.)
│   ├── stores/          # Pinia global reactive state management
│   ├── services/        # HTTP API & WebSocket communication clients
│   ├── router/          # Vue Router navigation configurations
│   ├── types/           # TypeScript interfaces & domain types
│   ├── assets/          # CSS styles (Tailwind), icons, brand graphics
│   ├── App.vue          # Root application component & layout shell
│   └── main.ts          # Application entrypoint & plugin registration
├── index.html           # Single-page HTML entry
├── package.json         # Dependencies (Vue, Pinia, Vue Flow, ECharts, Vite, Tailwind)
├── tailwind.config.js   # Tailwind design tokens and styling config
├── tsconfig.json        # TypeScript compiler options
└── vite.config.ts       # Vite bundler & dev server proxy configuration
```

### What kinds of files go in each folder:

* **`components/` (Reusable UI Widgets)**:
  * `ServiceCard.vue`: Displays a service's name, port, status badge, PID, and latency response time.
  * `HealthStatus.vue`: Color-coded health badge (`RUNNING`, `WARNING`, `DOWN`, `UNKNOWN`).
  * `EventTimeline.vue`: Chronological stream of recent system/service events.
  * `DependencyGraph.vue`: Interactive node-based architectural graph powered by **Vue Flow**.
  * `MetricChart.vue`: Real-time charts for CPU, memory, and endpoint response times powered by **ECharts**.
  * `DiagnosticCard.vue`: High-visibility root-cause diagnosis banner (e.g. "Redis stopped 3.8s before /orders 500 error").
  * `SnapshotDiffView.vue`: Side-by-side comparison widget highlighting environment differences.

* **`views/` (Page Views)**:
  * `Dashboard.vue`: Central overview showing project health counts, dependency overview, active diagnostic banner, and recent events.
  * `Environment.vue`: Process table, listening ports, Docker container status, and machine resource usage.
  * `Services.vue`: Detailed list of monitored services, port assignments, and health probe logs.
  * `Dependencies.vue`: Full-canvas interactive dependency graph explorer.
  * `Timeline.vue`: Historical event explorer with search, filtering, and timestamp drill-down.
  * `Snapshots.vue`: Snapshot management and environment diff comparison view.
  * `Diagnostics.vue`: Dedicated root-cause analysis workspace and diagnostic history.

* **`stores/` (Pinia State Management)**:
  * `project.ts`: Active project metadata, project list, and directory path.
  * `services.ts`: List of active services, health status map, and selected service state.
  * `environment.ts`: Port allocation list, process list, Docker containers, and host metrics.
  * `diagnostics.ts`: Active root-cause findings, severity, and event timeline correlations.

* **`services/` (Networking & Transport)**:
  * `api.ts`: Centralized HTTP client (Axios / Fetch) for calling Spring Boot REST endpoints.
  * `websocket.ts`: WebSocket client handling connection lifecycle and subscribing to `/topic/events` and `/topic/diagnostics`.

* **`router/`**:
  * `index.ts`: Route definitions mapping URLs (`/`, `/services`, `/dependencies`, `/timeline`, `/snapshots`, `/diagnostics`) to their corresponding views.

* **`types/`**:
  * TypeScript interfaces:
    * `service.ts` (`Service`, `ServiceStatus`, `HealthCheck`)
    * `event.ts` (`EnvironmentEvent`, `EventType`)
    * `diagnostic.ts` (`Diagnosis`, `Severity`, `Evidence`)
    * `snapshot.ts` (`Snapshot`, `EnvironmentDiff`)
