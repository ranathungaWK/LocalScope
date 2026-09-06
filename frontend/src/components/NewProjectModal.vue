<script setup lang="ts">
import { ref } from 'vue';
import { useProjectStore } from '../stores/project';
import { X, FolderPlus, AlertCircle } from 'lucide-vue-next';

const emit = defineEmits(['close']);
const store = useProjectStore();

const name = ref('');
const path = ref('');
const errorMessage = ref<string | null>(null);
const submitting = ref(false);

async function handleSubmit() {
  if (!name.value.trim() || !path.value.trim()) {
    errorMessage.value = 'Please provide both a project name and folder path.';
    return;
  }

  submitting.value = true;
  errorMessage.value = null;

  try {
    await store.createProject(name.value.trim(), path.value.trim());
    emit('close');
  } catch (err: any) {
    errorMessage.value = err.message || 'Failed to save project';
  } finally {
    submitting.value = false;
  }
}
</script>

<template>
  <div class="fixed inset-0 z-50 flex items-center justify-center bg-slate-950/80 backdrop-blur-sm p-4">
    <div class="w-full max-w-md rounded-xl border border-slate-800 bg-slate-900 p-6 shadow-2xl animate-in fade-in zoom-in-95 duration-150">
      <div class="flex items-center justify-between pb-4 border-b border-slate-800">
        <div class="flex items-center gap-2">
          <FolderPlus class="w-5 h-5 text-emerald-400" />
          <h3 class="text-base font-semibold text-slate-100">Register Local Project</h3>
        </div>
        <button
          @click="$emit('close')"
          class="text-slate-400 hover:text-slate-200 transition p-1 rounded-md hover:bg-slate-800"
        >
          <X class="w-4 h-4" />
        </button>
      </div>

      <form @submit.prevent="handleSubmit" class="mt-4 space-y-4">
        <div v-if="errorMessage" class="p-3 rounded-md bg-rose-500/10 border border-rose-500/20 text-xs text-rose-400 flex items-center gap-2">
          <AlertCircle class="w-4 h-4 shrink-0" />
          <span>{{ errorMessage }}</span>
        </div>

        <div>
          <label class="block text-xs font-medium text-slate-300 mb-1">Project Name</label>
          <input
            v-model="name"
            type="text"
            placeholder="e.g. MyShop"
            required
            class="w-full px-3 py-2 rounded-md bg-slate-950 border border-slate-800 text-sm text-slate-100 focus:outline-none focus:border-emerald-500 transition placeholder:text-slate-600 font-sans"
          />
        </div>

        <div>
          <label class="block text-xs font-medium text-slate-300 mb-1">Root Folder Path</label>
          <input
            v-model="path"
            type="text"
            placeholder="e.g. c:/KR/work/MyShop or /home/dev/myshop"
            required
            class="w-full px-3 py-2 rounded-md bg-slate-950 border border-slate-800 text-sm text-slate-100 focus:outline-none focus:border-emerald-500 transition placeholder:text-slate-600 font-mono text-xs"
          />
        </div>

        <div class="flex items-center justify-end gap-3 pt-4 border-t border-slate-800 mt-6">
          <button
            type="button"
            @click="$emit('close')"
            class="px-3.5 py-1.5 rounded-md text-xs font-medium text-slate-400 hover:text-slate-200 hover:bg-slate-800 transition"
          >
            Cancel
          </button>
          <button
            type="submit"
            :disabled="submitting"
            class="px-4 py-1.5 rounded-md text-xs font-medium bg-emerald-500 hover:bg-emerald-400 text-slate-950 font-semibold transition disabled:opacity-50"
          >
            {{ submitting ? 'Saving...' : 'Register Project' }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
