<script setup lang="ts">
import type { DiagnosticFinding } from '../types';
import { AlertTriangle, Clock, Server, ArrowRight } from 'lucide-vue-next';

defineProps<{
  finding: DiagnosticFinding;
}>();
</script>

<template>
  <div class="relative overflow-hidden rounded-lg border border-rose-500/30 bg-gradient-to-r from-rose-950/40 via-slate-900 to-slate-900 p-5 shadow-sm">
    <div class="flex items-start justify-between gap-4">
      <div class="flex items-start gap-3">
        <div class="p-2 rounded bg-rose-500/10 border border-rose-500/20 text-rose-400 mt-0.5">
          <AlertTriangle class="w-5 h-5" />
        </div>
        <div>
          <div class="flex items-center gap-2">
            <span class="text-xs font-semibold tracking-wider uppercase text-rose-400 font-mono">Possible Root Cause</span>
            <span class="inline-flex items-center px-1.5 py-0.5 rounded text-[11px] font-medium bg-rose-500/20 text-rose-300 border border-rose-500/30">
              Confidence: {{ finding.confidence }}
            </span>
          </div>
          <h3 class="text-base font-semibold text-slate-100 mt-1">
            {{ finding.title }}
          </h3>
          <p class="text-sm text-slate-300 mt-1 max-w-3xl leading-relaxed">
            {{ finding.description }}
          </p>

          <!-- Evidence Badges -->
          <div class="flex flex-wrap items-center gap-4 mt-3 text-xs text-slate-400">
            <div class="flex items-center gap-1.5">
              <Clock class="w-3.5 h-3.5 text-slate-500" />
              <span>Time Delta: <strong class="text-slate-200 font-mono">{{ finding.timeDeltaSeconds }}s earlier</strong></span>
            </div>
            <div class="flex items-center gap-1.5">
              <Server class="w-3.5 h-3.5 text-slate-500" />
              <span>Cause: <strong class="text-rose-300 font-mono">{{ finding.rootCauseService }}</strong></span>
            </div>
            <div class="flex items-center gap-1.5">
              <ArrowRight class="w-3.5 h-3.5 text-slate-500" />
              <span>Impact: <strong class="text-slate-200 font-mono">{{ finding.affectedService }} ({{ finding.affectedEndpoint }})</strong></span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
