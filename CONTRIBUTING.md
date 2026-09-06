# Contributing to LocalScope

Thank you for your interest in contributing to **LocalScope**! LocalScope is an open-source, 100% local developer tool designed to observe local development environments and identify what changed when applications break.

---

## 1. Prerequisites

Before getting started, ensure you have the following installed:
* **Java 21** (JDK 21 or later)
* **Maven 3.9+** (or use the included Maven wrapper)
* **Node.js 20+** and **npm** *(Only required for frontend UI development)*
* **Git**

---

## 2. Local Development Setup

LocalScope is organized into two main workspaces: the Spring Boot backend and the Vue 3 frontend.

### A. Clone & Switch to `dev` Branch
All active development and contributions take place on the **`dev`** branch. Always checkout `dev` before starting:

```bash
# Clone the repository (or your fork)
git clone https://github.com/ranathungaWK/LocalScope.git
cd LocalScope

# Switch to the dev branch and pull the latest changes
git checkout dev
git pull origin dev
```

### B. Run the Backend
```bash
cd backend
mvn spring-boot:run
```
* The backend will start at `http://localhost:9090`.
* An embedded SQLite database will automatically be initialized at `~/.localscope/localscope.db`.

### C. Run the Frontend
```bash
cd frontend
npm install
npm run dev
```
* The Vite dev server will start at `http://localhost:5173` with proxying configured to the backend.

---

## 3. Contribution Workflow

1. **Find an Issue:**
   * If you're new, check out issues labeled **`good first issue`** or **`help wanted`**.
   * Leave a comment on the issue asking to work on it so others know it's being addressed and avoid duplicate work.

2. **Fork, Clone & Branch from `dev`:**
   * Fork the repository to your GitHub account.
   * **Important:** Always base your feature branch on the latest **`dev`** branch (not `main`):
     ```bash
     # Ensure you are on dev and have the latest commits
     git checkout dev
     git pull origin dev

     # Create a descriptive feature or bugfix branch from dev
     git checkout -b feature/your-feature-name
     # or
     git checkout -b fix/issue-description
     ```

3. **Make Your Changes:**
   * Adhere to the architecture documented in [ARCHITECTURE.md](file:///c:/KR/work/LocalScope/ARCHITECTURE.md).
   * Follow the standard Spring Boot layered structure in `backend/` (`controller`, `service`, `repository`, `entity`, `dto`, `config`, `exception`).
   * Follow Vue 3 Composition API (`<script setup lang="ts">`) and Tailwind CSS conventions in `frontend/`.
   * **Core Rule:** Keep LocalScope **100% local** — never introduce cloud dependencies, external trackers, or third-party telemetry.

4. **Commit & Push:**
   * Write clear, conventional commit messages:
     ```bash
     git commit -m "feat(diagnostics): add latency anomaly detection rule"
     git push origin feature/your-feature-name
     ```

5. **Open a Pull Request:**
   * Open a Pull Request targeting the **`dev`** branch (not `main`). Reference the issue number in your PR description (e.g. `Closes #12`).
   * Include a brief summary of your changes and any relevant testing steps.

---

## 4. Reporting Issues & Suggestions

* **Bugs:** If you encounter unexpected behavior or port/process detection errors, open an issue detailing your operating system, running services, and steps to reproduce.
* **Feature Requests:** Feel free to open an issue or start a discussion for new diagnostic rules, collectors, or UI enhancements!
