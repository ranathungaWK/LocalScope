<script setup lang="ts">
import { onMounted, onUnmounted } from 'vue';
import { useProjectStore } from './stores/project';
import Navbar from './components/Navbar.vue';

const store = useProjectStore();
let intervalId: any = null;

onMounted(() => {
  store.loadProjects();
  // Poll backend health every 4 seconds
  intervalId = setInterval(() => {
    store.checkBackend();
  }, 4000);
});

onUnmounted(() => {
  if (intervalId) clearInterval(intervalId);
});
</script>

<template>
  <div class="min-h-screen bg-slate-950 text-slate-100 flex flex-col font-sans">
    <Navbar />

    <main class="flex-1 max-w-7xl w-full mx-auto px-6 py-6">
      <router-view />
    </main>

    <footer class="border-t border-slate-900 py-4 px-6 text-center text-xs text-slate-500 font-mono">
      LocalScope — Local Development Observability & Diagnostics • 100% Local Engine
    </footer>
  </div>
</template>
