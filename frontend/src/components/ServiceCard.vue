<script setup lang="ts">
import type { MonitoredService } from '../types';
import HealthBadge from './HealthBadge.vue';
import { Server } from 'lucide-vue-next';

defineProps<{
  service: MonitoredService;
}>();
</script>

<template>
  <div class="p-4 rounded-lg border border-slate-800 bg-slate-900/70 hover:border-slate-700 transition shadow-sm">
    <div class="flex items-start justify-between">
      <div>
        <div class="flex items-center gap-2">
          <Server class="w-4 h-4 text-slate-400" />
          <h3 class="text-sm font-semibold text-slate-100">{{ service.name }}</h3>
        </div>
        <p class="text-xs text-slate-500 mt-0.5">{{ service.type }}</p>
      </div>
      <HealthBadge :status="service.status" />
    </div>

    <div class="grid grid-cols-3 gap-2 mt-4 pt-3 border-t border-slate-800/80 text-xs font-mono">
      <div>
        <span class="text-slate-500 block text-[11px]">Port</span>
        <span class="font-semibold text-emerald-400">:{{ service.port }}</span>
      </div>
      <div>
        <span class="text-slate-500 block text-[11px]">PID</span>
        <span class="text-slate-300">{{ service.pid || '—' }}</span>
      </div>
      <div>
        <span class="text-slate-500 block text-[11px]">Response</span>
        <span :class="{
          'text-emerald-400': service.responseTimeMs && service.responseTimeMs < 100,
          'text-amber-400': service.responseTimeMs && service.responseTimeMs >= 100,
          'text-slate-500': !service.responseTimeMs
        }">
          {{ service.responseTimeMs ? `${service.responseTimeMs} ms` : '—' }}
        </span>
      </div>
    </div>
  </div>
</template>
