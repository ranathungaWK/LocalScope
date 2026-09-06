# LocalScope CLI & Packaging (`cli/`)

The **CLI & Packaging** layer packages LocalScope into a native local developer tool. It provides a single command-line interface (`localscope start`) to spin up the Spring Boot backend, launch the background agent, ensure SQLite is initialized, and open the web dashboard in the user's default browser.

---

## High-Level Folder Structure & File Responsibilities

```text
cli/
├── bin/                 # Executable entrypoints for Linux/macOS and Windows
├── scripts/             # Build, bundle, and installer creation scripts
└── README.md            # CLI architecture and usage documentation
```

### What kinds of files go in each folder:

* **`bin/` (Launcher Executables)**:
  * `localscope`: Unix/macOS bash script for `localscope start`, `stop`, `status`, and `logs`.
  * `localscope.cmd` / `localscope.ps1`: Windows launcher scripts.

* **`scripts/` (Build & Packaging)**:
  * `build-all.sh` / `build-all.ps1`: Orchestrates compiling the frontend (Vite build), copying static assets into Spring Boot's `resources/static/`, and packaging the backend and agent JARs.
  * `package-distribution.sh`: Bundles the binaries and runtimes into a local installer or standalone directory distribution.
