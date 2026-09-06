# LocalScope — Architecture

A local developer tool that monitors your local environment and identifies what changed when services break.

---

## 1. System Overview

```text
+-------------------------------------------------------------------------+
|                             Browser Web UI                              |
|                        (Vue 3 + Vite + Tailwind)                        |
+-------------------------------------------------------------------------+
                                     ▲
                                     │ HTTP / WebSocket
                                     ▼
+-------------------------------------------------------------------------+
|                           Spring Boot Backend                           |
|                                                                         |
|  +----------------------------------+ +------------------------------+  |
|  |         REST Controllers         | |       WebSocket Broker       |  |
|  +----------------------------------+ +------------------------------+  |
|                                                                         |
|  +----------------------------------+ +------------------------------+  |
|  |   Project & Dependency Scanner   | |    Diagnostic Rule Engine    |  |
|  +----------------------------------+ +------------------------------+  |
|                                                                         |
|  +----------------------------------+ +------------------------------+  |
|  |      Snapshot & Diff Engine      | |   Timeline Event Pipeline    |  |
|  +----------------------------------+ +------------------------------+  |
|                                                                         |
|  +----------------------------------+ +------------------------------+  |
|  |     Process & Port Observers     | |   Docker & Resource Monitors |  |
|  +----------------------------------+ +------------------------------+  |
|                                                                         |
|  +-------------------------------------------------------------------+  |
|  |                 Data Access Layer (JPA Repositories)              |  |
|  +-------------------------------------------------------------------+  |
+-------------------------------------------------------------------------+
                    ▲                                  ▲
    Internal SQLite │                                  │ Continuous Polling
                    ▼                                  ▼
+-----------------------------+      +------------------------------------+
|      Local SQLite DB        |      |            Host Machine            |
|   • Projects   • Events     |      |   • Processes (Java, Node, etc.)   |
|   • Services   • Snapshots  |      |   • Ports (3000, 8080, etc.)       |
|   • Diagnostics             |      |   • Docker Containers              |
+-----------------------------+      +------------------------------------+
```

---

## 2. Core Backend Components (Blocks)

* **`REST Controllers`**: Exposes HTTP endpoints for frontend data retrieval and project controls.
* **`WebSocket Broker`**: Streams real-time timeline events, service status updates, and diagnostic alerts.
* **`Project & Dependency Scanner`**: Automatically inspects project directories (`package.json`, `pom.xml`, `docker-compose.yml`) and builds dependency graphs.
* **`Diagnostic Rule Engine`**: Evaluates root-cause rules and computes time correlations (*"Redis stopped 3.8s before API failure"*).
* **`Snapshot & Diff Engine`**: Captures working environment states and detects configuration drift against broken states.
* **`Timeline Event Pipeline`**: Standardizes raw environment changes into chronological domain events.
* **`Process & Port Observers`**: Background scheduled tasks tracking running processes and detecting port conflicts.
* **`Docker & Resource Monitors`**: Background monitors tracking container lifecycles and host CPU/RAM metrics.
* **`Data Access Layer`**: Spring Data JPA repositories managing persistence into embedded SQLite.

---

## 3. Database Schema (SQLite Tables)

* **`projects`**: Registered local codebases (`id`, `name`, `path`, `created_at`).
* **`services`**: Monitored application services (`id`, `project_id`, `name`, `type`, `port`, `pid`, `status`).
* **`dependencies`**: Service-to-service relationships (`id`, `source_service_id`, `target_service_id`, `dependency_type`).
* **`events`**: Historical environment timeline (`id`, `service_id`, `event_type`, `timestamp`, `metadata`).
* **`health_checks`**: Latency & uptime probe records (`id`, `service_id`, `status`, `response_time`, `timestamp`).
* **`snapshots`**: Saved working environment baselines (`id`, `project_id`, `created_at`, `metadata`).
* **`environment_changes`**: Property differences between snapshots (`id`, `snapshot_id`, `property`, `old_value`, `new_value`).
* **`diagnostics`**: Root-cause reports & evidence (`id`, `project_id`, `severity`, `message`, `confidence`, `created_at`).


