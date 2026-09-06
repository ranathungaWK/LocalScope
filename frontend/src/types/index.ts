export interface Project {
  id: number;
  name: string;
  path: string;
  createdAt: string;
}

export type ServiceStatus = 'RUNNING' | 'WARNING' | 'DOWN' | 'UNKNOWN';

export interface MonitoredService {
  id: string;
  name: string;
  type: string;
  port: number;
  pid?: number;
  status: ServiceStatus;
  responseTimeMs?: number;
  dependencies?: string[];
  command?: string;
}

export interface TimelineEvent {
  id: string;
  timestamp: string;
  service: string;
  type: 'STARTED' | 'STOPPED' | 'LATENCY_SPIKE' | 'ERROR_500' | 'PORT_CONFLICT';
  message: string;
  severity: 'INFO' | 'WARN' | 'ERROR';
}

export interface DiagnosticFinding {
  id: string;
  title: string;
  affectedService: string;
  affectedEndpoint: string;
  rootCauseService: string;
  timeDeltaSeconds: number;
  confidence: 'HIGH' | 'MEDIUM' | 'LOW';
  description: string;
  timestamp: string;
}

export interface BackendHealth {
  status: string;
  service: string;
  version: string;
  timestamp: string;
  message?: string;
}
