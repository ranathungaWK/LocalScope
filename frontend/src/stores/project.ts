import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import { api } from '../services/api';
import type { Project, BackendHealth, MonitoredService, TimelineEvent, DiagnosticFinding } from '../types';

export const useProjectStore = defineStore('project', () => {
  const projects = ref<Project[]>([]);
  const activeProject = ref<Project | null>(null);
  const backendHealth = ref<BackendHealth | null>(null);
  const isBackendOnline = ref<boolean>(false);
  const loading = ref<boolean>(false);
  const error = ref<string | null>(null);

  // Observability state
  const services = ref<MonitoredService[]>(api.getMockServices());
  const timeline = ref<TimelineEvent[]>(api.getMockTimeline());
  const diagnostics = ref<DiagnosticFinding[]>(api.getMockDiagnostics());

  // Metrics summary
  const healthyCount = computed(() => services.value.filter(s => s.status === 'RUNNING').length);
  const warningCount = computed(() => services.value.filter(s => s.status === 'WARNING').length);
  const downCount = computed(() => services.value.filter(s => s.status === 'DOWN').length);

  async function checkBackend() {
    try {
      const health = await api.checkHealth();
      backendHealth.value = health;
      isBackendOnline.value = true;
      error.value = null;
    } catch {
      isBackendOnline.value = false;
      backendHealth.value = null;
    }
  }

  async function loadProjects() {
    loading.value = true;
    try {
      await checkBackend();
      if (isBackendOnline.value) {
        const [list, sList, tList, dList] = await Promise.all([
          api.getProjects(),
          api.getServices(),
          api.getTimeline(),
          api.getDiagnostics(),
        ]);
        projects.value = list;
        services.value = sList;
        timeline.value = tList;
        diagnostics.value = dList;
        if (list.length > 0 && !activeProject.value) {
          activeProject.value = list[0];
        }
      }
    } catch (err: any) {
      error.value = err.message;
    } finally {
      loading.value = false;
    }
  }

  async function createProject(name: string, path: string) {
    loading.value = true;
    try {
      const created = await api.createProject({ name, path });
      projects.value.push(created);
      activeProject.value = created;
      return created;
    } catch (err: any) {
      error.value = err.message;
      throw err;
    } finally {
      loading.value = false;
    }
  }

  function setActiveProject(project: Project) {
    activeProject.value = project;
  }

  return {
    projects,
    activeProject,
    backendHealth,
    isBackendOnline,
    loading,
    error,
    services,
    timeline,
    diagnostics,
    healthyCount,
    warningCount,
    downCount,
    checkBackend,
    loadProjects,
    createProject,
    setActiveProject,
  };
});
