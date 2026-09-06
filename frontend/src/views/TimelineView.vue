<script setup lang="ts">
import { ref, computed } from 'vue';
import { useProjectStore } from '../stores/project';
import { Clock, Filter } from 'lucide-vue-next';

const store = useProjectStore();
const selectedSeverity = ref<string>('ALL');

const filteredEvents = computed(() => {
  if (selectedSeverity.value === 'ALL') return store.timeline;
  return store.timeline.filter(e => e.severity === selectedSeverity.value);
});
</script>

<template>
  <div class="space-y-6">
    <div class="flex items-center justify-between pb-4 border-b border-slate-800">
      <div>
        <h1 class="text-lg font-bold text-slate-100 flex items-center gap-2">
          <Clock class="w-5 h-5 text-emerald-400" />
          <span>Environment Event Timeline</span>
        </h1>
        <p class="text-xs text-slate-400 mt-1">
          Historical chronicle of process crashes, port conflicts, latency changes, and recovery events.
        </p>
      </div>

      <!-- Severity Filter -->
      <div class="flex items-center gap-2 bg-slate-900 border border-slate-800 p-1 rounded-lg text-xs">
        <Filter class="w-3.5 h-3.5 text-slate-500 ml-2" />
        <button
          v-for="sev in ['ALL', 'ERROR', 'WARN', 'INFO']"
          :key="sev"
          @click="selectedSeverity = sev"
          class="px-2.5 py-1 rounded font-mono font-medium transition"
          :class="selectedSeverity === sev ? 'bg-slate-800 text-slate-100' : 'text-slate-500 hover:text-slate-300'"
        >
          {{ sev }}
        </button>
      </div>
    </div>

    <!-- Events List -->
    <div class="space-y-3 font-mono text-xs">
      <div
        v-for="event in filteredEvents"
        :key="event.id"
        class="flex items-start gap-4 p-4 rounded-lg border bg-slate-900/60 shadow-sm transition"
        :class="{
          'border-rose-500/30 bg-rose-950/10 text-rose-300': event.severity === 'ERROR',
          'border-amber-500/20 bg-amber-950/10 text-amber-300': event.severity === 'WARN',
          'border-slate-800 text-slate-300': event.severity === 'INFO'
        }"
      >
        <span class="text-slate-400 font-semibold shrink-0">{{ event.timestamp }}</span>
        <span class="px-2 py-0.5 rounded text-[11px] font-semibold shrink-0 bg-slate-800 text-slate-200 border border-slate-700">
          {{ event.service }}
        </span>
        <span class="text-slate-100 leading-relaxed text-sm font-sans flex-1">
          {{ event.message }}
        </span>
        <span
          class="text-[10px] px-1.5 py-0.5 rounded uppercase font-bold shrink-0"
          :class="{
            'bg-rose-500/20 text-rose-300': event.severity === 'ERROR',
            'bg-amber-500/20 text-amber-300': event.severity === 'WARN',
            'bg-slate-800 text-slate-400': event.severity === 'INFO'
          }"
        >
          {{ event.severity }}
        </span>
      </div>
    </div>
  </div>
</template>
