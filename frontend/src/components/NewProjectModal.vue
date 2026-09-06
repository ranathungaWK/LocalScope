<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useProjectStore } from '../stores/project';
import { api } from '../services/api';
import { X, FolderPlus, AlertCircle, FolderSearch, Folder, CornerLeftUp, Check } from 'lucide-vue-next';

const emit = defineEmits(['close']);
const store = useProjectStore();

const name = ref('');
const path = ref('');
const errorMessage = ref<string | null>(null);
const submitting = ref(false);

// Local Auto-Discovery State
const detectedProjects = ref<{ name: string; path: string }[]>([]);
const selectedDetected = ref('');
const loadingDetected = ref(false);

// Folder Browser State
const showBrowser = ref(false);
const browserLoading = ref(false);
const currentBrowsePath = ref('');
const parentBrowsePath = ref<string | null>(null);
const browserDirectories = ref<{ name: string; path: string }[]>([]);

onMounted(async () => {
  loadingDetected.value = true;
  try {
    const list = await api.getDetectedProjects();
    detectedProjects.value = list;
  } finally {
    loadingDetected.value = false;
  }
});

function handleSelectDetected(event: Event) {
  const target = event.target as HTMLSelectElement;
  const chosen = detectedProjects.value.find(p => p.path === target.value);
  if (chosen) {
    name.value = chosen.name;
    path.value = chosen.path;
  }
}

async function openBrowser(targetPath?: string) {
  showBrowser.value = true;
  browserLoading.value = true;
  try {
    const data = await api.browseDirectory(targetPath);
    currentBrowsePath.value = data.currentPath;
    parentBrowsePath.value = data.parentPath;
    browserDirectories.value = data.directories;
  } catch (err: any) {
    errorMessage.value = err.message || 'Failed to open directory';
  } finally {
    browserLoading.value = false;
  }
}

function selectBrowserFolder(folder: { name: string; path: string }) {
  name.value = folder.name;
  path.value = folder.path;
  showBrowser.value = false;
}

function selectCurrentFolder() {
  const parts = currentBrowsePath.value.split('/');
  name.value = parts[parts.length - 1] || 'Project';
  path.value = currentBrowsePath.value;
  showBrowser.value = false;
}

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
  <Teleport to="body">
    <div
      class="fixed inset-0 z-50 flex items-center justify-center bg-slate-950/80 backdrop-blur-sm p-4 overflow-y-auto"
      @click.self="$emit('close')"
    >
      <div class="relative w-full max-w-lg my-auto rounded-xl border border-slate-800 bg-slate-900 p-6 shadow-2xl animate-in fade-in zoom-in-95 duration-150">
        <!-- Header -->
        <div class="flex items-center justify-between pb-4 border-b border-slate-800">
          <div class="flex items-center gap-2">
            <FolderPlus class="w-5 h-5 text-emerald-400" />
            <h3 class="text-base font-semibold text-slate-100">Register Local Project</h3>
          </div>
          <button
            type="button"
            @click="$emit('close')"
            class="text-slate-400 hover:text-slate-200 transition p-1 rounded-md hover:bg-slate-800"
          >
            <X class="w-4 h-4" />
          </button>
        </div>

        <!-- Form -->
        <form @submit.prevent="handleSubmit" class="mt-4 space-y-4">
          <div
            v-if="errorMessage"
            class="p-3 rounded-md bg-rose-500/10 border border-rose-500/20 text-xs text-rose-400 flex items-center gap-2"
          >
            <AlertCircle class="w-4 h-4 shrink-0" />
            <span>{{ errorMessage }}</span>
          </div>

          <!-- Quick Select Discovered Project -->
          <div class="p-3 rounded-lg bg-slate-950/60 border border-slate-800 space-y-1.5">
            <label class="block text-xs font-semibold text-emerald-400 flex items-center gap-1.5">
              <FolderSearch class="w-3.5 h-3.5" />
              <span>Select Discovered Project in Workspace:</span>
            </label>
            <select
              v-model="selectedDetected"
              @change="handleSelectDetected"
              class="w-full px-3 py-2 rounded-md bg-slate-900 border border-slate-800 text-xs text-slate-100 focus:outline-none focus:border-emerald-500 transition font-mono"
            >
              <option value="" disabled>-- Select a local project to auto-fill --</option>
              <option
                v-for="p in detectedProjects"
                :key="p.path"
                :value="p.path"
              >
                {{ p.name }} ({{ p.path }})
              </option>
            </select>
            <span class="text-[11px] text-slate-500 block">
              Selecting a project automatically sets the name and location.
            </span>
          </div>

          <!-- Project Name Input -->
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

          <!-- Root Folder Path Input + Browse Button -->
          <div>
            <div class="flex items-center justify-between mb-1">
              <label class="text-xs font-medium text-slate-300">Root Folder Path</label>
              <button
                type="button"
                @click="openBrowser(path || undefined)"
                class="text-xs font-medium text-emerald-400 hover:text-emerald-300 flex items-center gap-1 transition"
              >
                <FolderSearch class="w-3.5 h-3.5" />
                <span>{{ showBrowser ? 'Close Browser' : 'Browse Folders...' }}</span>
              </button>
            </div>

            <input
              v-model="path"
              type="text"
              placeholder="e.g. c:/KR/work/MyShop or /home/dev/myshop"
              required
              class="w-full px-3 py-2 rounded-md bg-slate-950 border border-slate-800 text-sm text-slate-100 focus:outline-none focus:border-emerald-500 transition placeholder:text-slate-600 font-mono text-xs"
            />
          </div>

          <!-- Interactive Folder Browser Drawer -->
          <div
            v-if="showBrowser"
            class="p-3 rounded-lg border border-slate-800 bg-slate-950 space-y-2 animate-in fade-in duration-100"
          >
            <div class="flex items-center justify-between text-xs font-mono text-slate-400 pb-2 border-b border-slate-800/80">
              <span class="truncate max-w-[280px]" :title="currentBrowsePath">📂 {{ currentBrowsePath }}</span>
              <div class="flex items-center gap-2">
                <button
                  v-if="parentBrowsePath"
                  type="button"
                  @click="openBrowser(parentBrowsePath)"
                  title="Go up one folder"
                  class="p-1 rounded bg-slate-800 hover:bg-slate-700 text-slate-300 flex items-center gap-1 text-[11px]"
                >
                  <CornerLeftUp class="w-3.5 h-3.5" />
                  <span>Up</span>
                </button>
                <button
                  type="button"
                  @click="selectCurrentFolder"
                  class="px-2 py-0.5 rounded bg-emerald-500/20 text-emerald-400 hover:bg-emerald-500/30 text-[11px] font-semibold flex items-center gap-1"
                >
                  <Check class="w-3 h-3" />
                  <span>Use This</span>
                </button>
              </div>
            </div>

            <div v-if="browserLoading" class="text-xs text-slate-500 py-3 text-center">
              Scanning directories...
            </div>

            <div v-else class="max-h-40 overflow-y-auto space-y-1 pr-1 font-mono text-xs">
              <div v-if="browserDirectories.length === 0" class="text-slate-500 italic py-2 text-center text-[11px]">
                No subdirectories found.
              </div>

              <div
                v-for="dir in browserDirectories"
                :key="dir.path"
                class="flex items-center justify-between p-1.5 rounded hover:bg-slate-900 group transition"
              >
                <button
                  type="button"
                  @click="openBrowser(dir.path)"
                  class="flex items-center gap-2 text-slate-300 hover:text-emerald-400 truncate text-left"
                >
                  <Folder class="w-3.5 h-3.5 text-slate-500 group-hover:text-emerald-400 shrink-0" />
                  <span class="truncate">{{ dir.name }}</span>
                </button>

                <button
                  type="button"
                  @click="selectBrowserFolder(dir)"
                  class="text-[11px] text-emerald-400 hover:underline px-1.5 py-0.5 rounded hover:bg-emerald-500/10 shrink-0"
                >
                  Select
                </button>
              </div>
            </div>
          </div>

          <!-- Action Buttons -->
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
  </Teleport>
</template>
