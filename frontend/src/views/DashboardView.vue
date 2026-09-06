<script setup lang="ts">
import { onMounted } from 'vue';
import { useProjectStore } from '../stores/project';
import DiagnosticCard from '../components/DiagnosticCard.vue';
import DependencyGraph from '../components/DependencyGraph.vue';
import EventTimeline from '../components/EventTimeline.vue';
import ServiceCard from '../components/ServiceCard.vue';
import { CheckCircle2, AlertTriangle, XCircle, RefreshCw } from 'lucide-vue-next';

const store = useProjectStore();

onMounted(async () => {
  await store.loadProjects();
});
</script>

<template>
  <div class="space-y-6">
    <!-- Top Summary Banner -->
    <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
      <div class="p-4 rounded-lg border border-slate-800 bg-slate-900/60 flex items-center justify-between">
        <div>
          <span class="text-xs text-slate-400 font-medium block">Active Project</span>
          <span class="text-base font-bold text-slate-100 font-mono mt-0.5 block">
            {{ store.activeProject?.name || 'MyShop (Demo)' }}
          </span>
        </div>
        <button
          @click="store.loadProjects()"
          title="Refresh state"
          class="p-2 rounded hover:bg-slate-800 text-slate-400 hover:text-slate-200 transition"
        >
          <RefreshCw class="w-4 h-4" :class="{ 'animate-spin': store.loading }" />
        </button>
      </div>

      <div class="p-4 rounded-lg border border-emerald-500/20 bg-emerald-950/10 flex items-center gap-3">
        <div class="p-2 rounded bg-emerald-500/10 text-emerald-400">
          <CheckCircle2 class="w-5 h-5" />
        </div>
        <div>
          <span class="text-xl font-bold text-emerald-400 font-mono">{{ store.healthyCount }}</span>
          <span class="text-xs text-emerald-500/90 font-medium block">Healthy Services</span>
        </div>
      </div>

      <div class="p-4 rounded-lg border border-amber-500/20 bg-amber-950/10 flex items-center gap-3">
        <div class="p-2 rounded bg-amber-500/10 text-amber-400">
          <AlertTriangle class="w-5 h-5" />
        </div>
        <div>
          <span class="text-xl font-bold text-amber-400 font-mono">{{ store.warningCount }}</span>
          <span class="text-xs text-amber-500/90 font-medium block">Degraded / Warning</span>
        </div>
      </div>

      <div class="p-4 rounded-lg border border-rose-500/20 bg-rose-950/10 flex items-center gap-3">
        <div class="p-2 rounded bg-rose-500/10 text-rose-400">
          <XCircle class="w-5 h-5" />
        </div>
        <div>
          <span class="text-xl font-bold text-rose-400 font-mono">{{ store.downCount }}</span>
          <span class="text-xs text-rose-500/90 font-medium block">Offline / Down</span>
        </div>
      </div>
    </div>

    <!-- Active Root Cause Diagnostic Alert (from LocalScope.md) -->
    <div v-if="store.diagnostics.length > 0">
      <DiagnosticCard :finding="store.diagnostics[0]" />
    </div>

    <!-- Center Section: Graph & Timeline side-by-side -->
    <div class="grid grid-cols-1 lg:grid-cols-12 gap-6">
      <!-- Dependency Graph (7 cols) -->
      <div class="lg:col-span-7">
        <DependencyGraph :services="store.services" />
      </div>

      <!-- Live Event Timeline (5 cols) -->
      <div class="lg:col-span-5">
        <EventTimeline :events="store.timeline" />
      </div>
    </div>

    <!-- Bottom Section: Monitored Services Grid -->
    <div>
      <div class="flex items-center justify-between mb-3">
        <h2 class="text-sm font-semibold text-slate-200">Monitored Service Instances</h2>
        <span class="text-xs text-slate-500 font-mono">{{ store.services.length }} processes observed</span>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-5 gap-4">
        <ServiceCard
          v-for="service in store.services"
          :key="service.id"
          :service="service"
        />
      </div>
    </div>
  </div>
</template>
