<script setup lang="ts">
import { ref } from 'vue';
import { useProjectStore } from '../stores/project';
import { Layers, ChevronDown, Plus } from 'lucide-vue-next';
import NewProjectModal from './NewProjectModal.vue';

const store = useProjectStore();
const isDropdownOpen = ref(false);
const showNewProjectModal = ref(false);

function selectProject(p: any) {
  store.setActiveProject(p);
  isDropdownOpen.value = false;
}
</script>

<template>
  <header class="sticky top-0 z-40 border-b border-slate-800 bg-slate-950/90 backdrop-blur-md px-6 py-3">
    <div class="max-w-7xl mx-auto flex items-center justify-between gap-4">
      <!-- Left: Logo & Project Selector -->
      <div class="flex items-center gap-6">
        <router-link to="/" class="flex items-center gap-2.5 text-slate-100 font-semibold tracking-tight hover:opacity-90 transition">
          <div class="w-7 h-7 rounded-lg bg-emerald-500/10 border border-emerald-500/30 flex items-center justify-center text-emerald-400">
            <Layers class="w-4 h-4" />
          </div>
          <span class="text-base font-bold text-slate-100">LocalScope</span>
          <span class="text-[11px] font-mono px-1.5 py-0.5 rounded bg-slate-800 text-slate-400 border border-slate-700">v0.1</span>
        </router-link>

        <!-- Project Selector Dropdown -->
        <div class="relative">
          <button
            @click="isDropdownOpen = !isDropdownOpen"
            class="flex items-center gap-2 px-3 py-1.5 rounded-lg border border-slate-800 bg-slate-900 hover:border-slate-700 text-xs font-medium text-slate-200 transition"
          >
            <span class="text-slate-500">Project:</span>
            <span class="font-semibold text-slate-100 font-mono">
              {{ store.activeProject ? store.activeProject.name : 'MyShop (Demo)' }}
            </span>
            <ChevronDown class="w-3.5 h-3.5 text-slate-400 ml-1" />
          </button>

          <!-- Dropdown Menu -->
          <div
            v-if="isDropdownOpen"
            class="absolute left-0 mt-2 w-64 rounded-lg border border-slate-800 bg-slate-900 p-2 shadow-xl z-50 animate-in fade-in zoom-in-95 duration-100"
          >
            <div class="text-[11px] font-semibold text-slate-500 px-2 py-1 uppercase tracking-wider">
              Monitored Projects
            </div>

            <div v-if="store.projects.length === 0" class="px-2 py-2 text-xs text-slate-500 italic">
              No saved projects yet.
            </div>

            <div v-else class="space-y-1">
              <button
                v-for="p in store.projects"
                :key="p.id"
                @click="selectProject(p)"
                class="w-full text-left px-2.5 py-1.5 rounded text-xs text-slate-300 hover:bg-slate-800 flex items-center justify-between transition"
                :class="{ 'bg-emerald-500/10 text-emerald-300 font-medium': store.activeProject?.id === p.id }"
              >
                <span class="truncate font-mono">{{ p.name }}</span>
                <span class="text-[10px] text-slate-500 truncate max-w-[100px]">{{ p.path }}</span>
              </button>
            </div>

            <div class="border-t border-slate-800 mt-2 pt-2">
              <button
                @click="showNewProjectModal = true; isDropdownOpen = false"
                class="w-full text-left px-2.5 py-1.5 rounded text-xs font-medium text-emerald-400 hover:bg-emerald-500/10 flex items-center gap-1.5 transition"
              >
                <Plus class="w-3.5 h-3.5" />
                <span>Register New Project</span>
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Center: Navigation Links -->
      <nav class="hidden md:flex items-center gap-1 text-xs font-medium">
        <router-link
          to="/"
          class="px-3 py-1.5 rounded-md transition"
          :class="$route.path === '/' ? 'text-slate-100 bg-slate-800/80 font-semibold' : 'text-slate-400 hover:text-slate-200 hover:bg-slate-900'"
        >
          Dashboard
        </router-link>
        <router-link
          to="/timeline"
          class="px-3 py-1.5 rounded-md transition"
          :class="$route.path === '/timeline' ? 'text-slate-100 bg-slate-800/80 font-semibold' : 'text-slate-400 hover:text-slate-200 hover:bg-slate-900'"
        >
          Timeline
        </router-link>
        <router-link
          to="/projects"
          class="px-3 py-1.5 rounded-md transition"
          :class="$route.path === '/projects' ? 'text-slate-100 bg-slate-800/80 font-semibold' : 'text-slate-400 hover:text-slate-200 hover:bg-slate-900'"
        >
          Projects (SQLite)
        </router-link>
      </nav>

      <!-- Right: Backend Health Status -->
      <div class="flex items-center gap-3">
        <div
          class="flex items-center gap-2 px-2.5 py-1 rounded-full text-xs font-mono border"
          :class="store.isBackendOnline
            ? 'bg-emerald-500/10 text-emerald-400 border-emerald-500/30'
            : 'bg-rose-500/10 text-rose-400 border-rose-500/30'"
        >
          <span
            class="w-2 h-2 rounded-full"
            :class="store.isBackendOnline ? 'bg-emerald-400 animate-pulse' : 'bg-rose-400'"
          ></span>
          <span>{{ store.isBackendOnline ? 'Backend :9090 UP' : 'Backend Offline' }}</span>
        </div>
      </div>
    </div>

    <!-- New Project Modal -->
    <NewProjectModal v-if="showNewProjectModal" @close="showNewProjectModal = false" />
  </header>
</template>
