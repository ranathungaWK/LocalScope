<script setup lang="ts">
import type { MonitoredService } from '../types';
import HealthBadge from './HealthBadge.vue';

defineProps<{
  services: MonitoredService[];
}>();
</script>

<template>
  <div class="rounded-lg border border-slate-800 bg-slate-900/60 p-5">
    <div class="flex items-center justify-between pb-4 mb-4 border-b border-slate-800/80">
      <div>
        <h2 class="text-sm font-semibold text-slate-200">Service Dependency Topology</h2>
        <p class="text-xs text-slate-500 mt-0.5">Inferred from project configs and observed network relationships</p>
      </div>
      <span class="text-xs font-mono text-slate-500">Live Graph</span>
    </div>

    <!-- Visual Architecture Layout -->
    <div class="flex flex-col items-center gap-6 py-4">
      <!-- Tier 1: Frontend Client -->
      <div class="w-64 p-3.5 rounded-lg border border-slate-700/80 bg-slate-800/90 shadow-sm transition hover:border-slate-600">
        <div class="flex items-center justify-between">
          <span class="text-sm font-semibold text-slate-100">React Frontend</span>
          <HealthBadge status="RUNNING" />
        </div>
        <div class="flex items-center justify-between mt-2 text-xs font-mono text-slate-400">
          <span class="text-emerald-400/90">:3000</span>
          <span>12 ms</span>
        </div>
      </div>

      <!-- Connector Line -->
      <div class="flex flex-col items-center -my-2 text-slate-600">
        <div class="w-px h-6 bg-slate-700"></div>
        <div class="w-1.5 h-1.5 rotate-45 border-b border-r border-slate-700"></div>
      </div>

      <!-- Tier 2: Backend Core -->
      <div class="w-72 p-4 rounded-lg border border-slate-700 bg-slate-800/95 shadow-md ring-1 ring-emerald-500/20">
        <div class="flex items-center justify-between">
          <span class="text-sm font-bold text-slate-100">Spring Boot Backend</span>
          <HealthBadge status="RUNNING" />
        </div>
        <div class="flex items-center justify-between mt-2 text-xs font-mono text-slate-400">
          <span class="text-emerald-400/90">:8080</span>
          <span>25 ms</span>
        </div>
      </div>

      <!-- Branching Connector -->
      <div class="w-full max-w-lg flex flex-col items-center -my-2 text-slate-600">
        <div class="w-px h-4 bg-slate-700"></div>
        <div class="w-3/4 border-t border-slate-700"></div>
        <div class="w-3/4 flex justify-between">
          <div class="w-px h-4 bg-slate-700"></div>
          <div class="w-px h-4 bg-slate-700"></div>
          <div class="w-px h-4 bg-slate-700"></div>
        </div>
      </div>

      <!-- Tier 3: Dependencies (Postgres, Redis, Python) -->
      <div class="grid grid-cols-3 gap-4 w-full max-w-xl">
        <!-- Redis (Down) -->
        <div class="p-3 rounded-lg border border-rose-500/40 bg-rose-950/20 shadow-sm">
          <div class="flex items-center justify-between">
            <span class="text-xs font-semibold text-rose-200">Redis</span>
            <HealthBadge status="DOWN" />
          </div>
          <div class="flex items-center justify-between mt-2 text-[11px] font-mono text-slate-400">
            <span class="text-rose-400">:6379</span>
            <span class="text-rose-400/80">Offline</span>
          </div>
        </div>

        <!-- PostgreSQL (Running) -->
        <div class="p-3 rounded-lg border border-slate-800 bg-slate-800/60 shadow-sm">
          <div class="flex items-center justify-between">
            <span class="text-xs font-semibold text-slate-200">PostgreSQL</span>
            <HealthBadge status="RUNNING" />
          </div>
          <div class="flex items-center justify-between mt-2 text-[11px] font-mono text-slate-400">
            <span class="text-emerald-400">:5432</span>
            <span>8 ms</span>
          </div>
        </div>

        <!-- Python ML (Warning) -->
        <div class="p-3 rounded-lg border border-amber-500/30 bg-amber-950/10 shadow-sm">
          <div class="flex items-center justify-between">
            <span class="text-xs font-semibold text-amber-200">ML API</span>
            <HealthBadge status="WARNING" />
          </div>
          <div class="flex items-center justify-between mt-2 text-[11px] font-mono text-slate-400">
            <span class="text-amber-400">:8000</span>
            <span class="text-amber-400">820 ms</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
