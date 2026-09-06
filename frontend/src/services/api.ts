import type { Project, BackendHealth, MonitoredService, TimelineEvent, DiagnosticFinding } from '../types';

export const api = {
  // Check Spring Boot backend health
  async checkHealth(): Promise<BackendHealth> {
    const res = await fetch('/api/v1/health');
    if (!res.ok) throw new Error('Backend offline');
    return res.json();
  },

  // Project endpoints (connected directly to Spring Boot + SQLite)
  async getProjects(): Promise<Project[]> {
    const res = await fetch('/api/v1/projects');
    if (!res.ok) throw new Error('Failed to fetch projects');
    return res.json();
  },

  async createProject(data: { name: string; path: string }): Promise<Project> {
    const res = await fetch('/api/v1/projects', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!res.ok) {
      const err = await res.json().catch(() => ({ message: 'Failed to create project' }));
      throw new Error(err.message || 'Failed to create project');
    }
    return res.json();
  },

  async deleteProject(id: number): Promise<void> {
    const res = await fetch(`/api/v1/projects/${id}`, { method: 'DELETE' });
    if (!res.ok) throw new Error('Failed to delete project');
  },

  // Discover local projects in workspace (e.g. C:/KR/work/)
  async getDetectedProjects(): Promise<{ name: string; path: string }[]> {
    try {
      const res = await fetch('/api/v1/system/detected-projects');
      if (res.ok) return await res.json();
    } catch {
      // fallback if offline
    }
    return [];
  },

  // Browse local folder structure
  async browseDirectory(path?: string): Promise<{ currentPath: string; parentPath: string | null; directories: { name: string; path: string }[] }> {
    const query = path ? `?path=${encodeURIComponent(path)}` : '';
    const res = await fetch(`/api/v1/system/browse${query}`);
    if (!res.ok) throw new Error('Failed to browse directory');
    return res.json();
  },

  // Live Monitored Services from Spring Boot API
  async getServices(): Promise<MonitoredService[]> {
    try {
      const res = await fetch('/api/v1/services');
      if (res.ok) {
        return await res.json();
      }
    } catch {
      // fallback
    }
    return this.getMockServices();
  },

  // Live Timeline Events from Spring Boot API
  async getTimeline(): Promise<TimelineEvent[]> {
    try {
      const res = await fetch('/api/v1/events');
      if (res.ok) {
        return await res.json();
      }
    } catch {
      // fallback
    }
    return this.getMockTimeline();
  },

  // Live Diagnostic Findings from Spring Boot API
  async getDiagnostics(): Promise<DiagnosticFinding[]> {
    try {
      const res = await fetch('/api/v1/diagnostics');
      if (res.ok) {
        return await res.json();
      }
    } catch {
      // fallback
    }
    return this.getMockDiagnostics();
  },

  // Default initial observable services
  getMockServices(): MonitoredService[] {
    return [
      { id: '1', name: 'React Frontend', type: 'Node/Vite', port: 3000, pid: 14208, status: 'RUNNING', responseTimeMs: 12, dependencies: ['Spring Boot Backend'] },
      { id: '2', name: 'Spring Boot Backend', type: 'Java/Spring', port: 8080, pid: 19844, status: 'RUNNING', responseTimeMs: 25, dependencies: ['PostgreSQL', 'Redis', 'Python ML API'] },
      { id: '3', name: 'PostgreSQL Database', type: 'Database', port: 5432, pid: 4892, status: 'RUNNING', responseTimeMs: 8, dependencies: [] },
      { id: '4', name: 'Redis Cache', type: 'Cache/Store', port: 6379, pid: 2190, status: 'DOWN', responseTimeMs: 0, dependencies: [] },
      { id: '5', name: 'Python ML API', type: 'FastAPI/Uvicorn', port: 8000, pid: 8740, status: 'WARNING', responseTimeMs: 820, dependencies: [] },
    ];
  },

  // Default initial timeline events
  getMockTimeline(): TimelineEvent[] {
    return [
      { id: 'e1', timestamp: '10:32:01', service: 'PostgreSQL', type: 'STARTED', message: 'PostgreSQL instance listening on :5432', severity: 'INFO' },
      { id: 'e2', timestamp: '10:32:04', service: 'Spring Boot', type: 'STARTED', message: 'Spring Boot backend started on :8080', severity: 'INFO' },
      { id: 'e3', timestamp: '10:32:06', service: 'Redis', type: 'STARTED', message: 'Redis cache connected on :6379', severity: 'INFO' },
      { id: 'e4', timestamp: '10:34:18', service: 'Redis', type: 'STOPPED', message: 'Redis process terminated unexpectedly (exit code 137)', severity: 'ERROR' },
      { id: 'e5', timestamp: '10:34:21', service: 'Spring Boot', type: 'LATENCY_SPIKE', message: 'GET /orders average latency increased from 35ms to 450ms', severity: 'WARN' },
      { id: 'e6', timestamp: '10:34:23', service: 'Spring Boot', type: 'ERROR_500', message: 'GET /orders returned HTTP 500 (JedisConnectionException)', severity: 'ERROR' },
    ];
  },

  // Default initial diagnostic findings
  getMockDiagnostics(): DiagnosticFinding[] {
    return [
      {
        id: 'd1',
        title: 'Redis Outage Correlated with Backend Failure',
        affectedService: 'Spring Boot Backend',
        affectedEndpoint: 'GET /orders',
        rootCauseService: 'Redis Cache (:6379)',
        timeDeltaSeconds: 3.8,
        confidence: 'HIGH',
        description: 'Redis became unavailable 3.8 seconds before /orders returned 500 errors. Spring Boot dependency tree indicates direct cache dependency.',
        timestamp: '10:34:23',
      },
    ];
  },
};
