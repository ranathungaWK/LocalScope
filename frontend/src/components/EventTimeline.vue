<script setup lang="ts">
import type { TimelineEvent } from '../types';

defineProps<{
  events: TimelineEvent[];
}>();
</script>

<template>
  <div class="rounded-lg border border-slate-800 bg-slate-900/60 p-5">
    <div class="flex items-center justify-between pb-4 mb-4 border-b border-slate-800/80">
      <div>
        <h2 class="text-sm font-semibold text-slate-200">Environment Timeline</h2>
        <p class="text-xs text-slate-500 mt-0.5">Chronological record of environment state changes</p>
      </div>
      <span class="text-xs font-mono text-slate-500">{{ events.length }} events</span>
    </div>

    <div class="space-y-3 font-mono text-xs">
      <div
        v-for="event in events"
        :key="event.id"
        class="flex items-start gap-3 p-2.5 rounded border transition"
        :class="{
          'border-rose-500/30 bg-rose-950/20 text-rose-300': event.severity === 'ERROR',
          'border-amber-500/20 bg-amber-950/10 text-amber-300': event.severity === 'WARN',
          'border-slate-800/80 bg-slate-900/40 text-slate-300': event.severity === 'INFO'
        }"
      >
        <!-- Timestamp -->
        <span class="text-slate-500 shrink-0 font-medium">{{ event.timestamp }}</span>

        <!-- Service Tag -->
        <span class="px-1.5 py-0.5 rounded text-[11px] font-semibold shrink-0 bg-slate-800/80 text-slate-200 border border-slate-700/60">
          {{ event.service }}
        </span>

        <!-- Message -->
        <span class="text-slate-200 break-all leading-relaxed">{{ event.message }}</span>
      </div>
    </div>
  </div>
</template>
