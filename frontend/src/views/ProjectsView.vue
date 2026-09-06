<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useProjectStore } from '../stores/project';
import { api } from '../services/api';
import { FolderGit2, Plus, Trash2, Database, AlertCircle } from 'lucide-vue-next';
import NewProjectModal from '../components/NewProjectModal.vue';

const store = useProjectStore();
const showModal = ref(false);
const deletingId = ref<number | null>(null);

onMounted(() => {
  store.loadProjects();
});

async function handleDelete(id: number) {
  if (!confirm('Are you sure you want to remove this project from LocalScope monitoring?')) return;
  deletingId.value = id;
  try {
    await api.deleteProject(id);
    await store.loadProjects();
  } catch (err: any) {
    alert(err.message || 'Failed to delete project');
  } finally {
    deletingId.value = null;
  }
}
</script>

<template>
  <div class="space-y-6">
    <div class="flex items-center justify-between pb-4 border-b border-slate-800">
      <div>
        <h1 class="text-lg font-bold text-slate-100 flex items-center gap-2">
          <Database class="w-5 h-5 text-emerald-400" />
          <span>Local Projects (SQLite Persistence)</span>
        </h1>
        <p class="text-xs text-slate-400 mt-1">
          Registered codebases stored locally in <code class="text-emerald-400 font-mono bg-slate-900 px-1 py-0.5 rounded border border-slate-800">~/.localscope/localscope.db</code>
        </p>
      </div>

      <button
        @click="showModal = true"
        class="flex items-center gap-2 px-3.5 py-1.5 rounded-lg bg-emerald-500 hover:bg-emerald-400 text-slate-950 text-xs font-semibold transition"
      >
        <Plus class="w-4 h-4" />
        <span>Register Project</span>
      </button>
    </div>

    <!-- Backend Offline Banner Warning if needed -->
    <div v-if="!store.isBackendOnline" class="p-4 rounded-lg bg-amber-500/10 border border-amber-500/20 text-amber-300 text-xs flex items-center gap-3">
      <AlertCircle class="w-5 h-5 shrink-0 text-amber-400" />
      <div>
        <strong class="font-semibold">Spring Boot backend is not responding on :9090.</strong>
        <p class="mt-0.5 text-slate-400">Make sure your backend is running (<code class="text-amber-300 font-mono">mvn spring-boot:run</code> in the backend folder).</p>
      </div>
    </div>

    <!-- Projects Table -->
    <div class="rounded-lg border border-slate-800 bg-slate-900/60 overflow-hidden shadow-sm">
      <table class="w-full text-left text-xs">
        <thead class="bg-slate-950/80 border-b border-slate-800 text-slate-400 uppercase font-mono text-[11px]">
          <tr>
            <th class="px-5 py-3">ID</th>
            <th class="px-5 py-3">Project Name</th>
            <th class="px-5 py-3">Root Path</th>
            <th class="px-5 py-3">Registered At</th>
            <th class="px-5 py-3 text-right">Actions</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-slate-800/80 text-slate-200">
          <tr v-if="store.projects.length === 0">
            <td colspan="5" class="px-5 py-8 text-center text-slate-500 italic">
              No projects saved in SQLite yet. Click "Register Project" above to add your first local codebase!
            </td>
          </tr>

          <tr
            v-for="p in store.projects"
            :key="p.id"
            class="hover:bg-slate-800/40 transition"
          >
            <td class="px-5 py-3 font-mono text-slate-500">#{{ p.id }}</td>
            <td class="px-5 py-3 font-semibold text-slate-100 flex items-center gap-2">
              <FolderGit2 class="w-4 h-4 text-emerald-400" />
              <span>{{ p.name }}</span>
            </td>
            <td class="px-5 py-3 font-mono text-slate-400">{{ p.path }}</td>
            <td class="px-5 py-3 text-slate-500 font-mono text-[11px]">{{ new Date(p.createdAt).toLocaleString() }}</td>
            <td class="px-5 py-3 text-right">
              <button
                @click="handleDelete(p.id)"
                :disabled="deletingId === p.id"
                title="Remove project"
                class="p-1.5 rounded text-slate-400 hover:text-rose-400 hover:bg-rose-500/10 transition"
              >
                <Trash2 class="w-4 h-4" />
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Register Modal -->
    <NewProjectModal v-if="showModal" @close="showModal = false; store.loadProjects()" />
  </div>
</template>
