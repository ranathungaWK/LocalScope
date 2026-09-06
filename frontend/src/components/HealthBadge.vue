<script setup lang="ts">
import type { ServiceStatus } from '../types';

defineProps<{
  status: ServiceStatus;
}>();
</script>

<template>
  <span
    v-if="status === 'RUNNING'"
    class="inline-flex items-center gap-1.5 px-2 py-0.5 rounded text-xs font-medium bg-emerald-500/10 text-emerald-400 border border-emerald-500/20"
  >
    <span class="w-1.5 h-1.5 rounded-full bg-emerald-400 animate-pulse"></span>
    Running
  </span>

  <span
    v-else-if="status === 'WARNING'"
    class="inline-flex items-center gap-1.5 px-2 py-0.5 rounded text-xs font-medium bg-amber-500/10 text-amber-400 border border-amber-500/20"
  >
    <span class="w-1.5 h-1.5 rounded-full bg-amber-400"></span>
    Warning
  </span>

  <span
    v-else-if="status === 'DOWN'"
    class="inline-flex items-center gap-1.5 px-2 py-0.5 rounded text-xs font-medium bg-rose-500/10 text-rose-400 border border-rose-500/20"
  >
    <span class="w-1.5 h-1.5 rounded-full bg-rose-400"></span>
    Down
  </span>

  <span
    v-else
    class="inline-flex items-center gap-1.5 px-2 py-0.5 rounded text-xs font-medium bg-slate-800 text-slate-400 border border-slate-700"
  >
    <span class="w-1.5 h-1.5 rounded-full bg-slate-400"></span>
    Unknown
  </span>
</template>
