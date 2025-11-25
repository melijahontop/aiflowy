<script setup lang="ts">
import { onMounted, onUnmounted, ref } from 'vue';

import { ArrowLeft, Position } from '@element-plus/icons-vue';
import { Tinyflow } from '@tinyflow-ai/vue';
import { ElButton } from 'element-plus';

import { router } from '#/router';

import '@tinyflow-ai/vue/dist/index.css';

// vue
onMounted(() => {
  document.addEventListener('keydown', handleKeydown);
});
onUnmounted(() => {
  document.removeEventListener('keydown', handleKeydown);
});
// variables
const saveLoading = ref(false);
const handleKeydown = (event: KeyboardEvent) => {
  // 检查是否是 Ctrl+S
  if (event.ctrlKey && event.key === 's') {
    event.preventDefault(); // 阻止浏览器默认保存行为
    if (!saveLoading.value) {
      handleSave();
    }
  }
};
const initialData = ref({
  nodes: [],
  edges: [],
});
// functions
async function runWorkflow() {
  if (!saveLoading.value) {
    await handleSave();
    console.log('试运行');
  }
}
async function handleSave() {
  console.log('保存数据');
  saveLoading.value = true;
  // 模拟请求接口
  await new Promise((resolve: any) => setTimeout(resolve, 1000));
  saveLoading.value = false;
}
</script>

<template>
  <div class="head-div h-full w-full">
    <div class="flex items-center justify-between border-b p-2.5">
      <div>
        <ElButton :icon="ArrowLeft" link @click="router.back()">
          工作流名称
        </ElButton>
      </div>
      <div>
        <ElButton :disabled="saveLoading" :icon="Position" @click="runWorkflow">
          试运行
        </ElButton>
        <ElButton type="primary" :disabled="saveLoading" @click="handleSave">
          保存(ctrl+s)
        </ElButton>
      </div>
    </div>
    <Tinyflow class="tiny-flow-container" :data="initialData" />
  </div>
</template>

<style scoped>
:deep(.tf-toolbar-container-body) {
  height: calc(100vh - 365px) !important;
  overflow-y: auto;
}
:deep(.agentsflow) {
  height: calc(100vh - 130px) !important;
}
.head-div {
  background-color: var(--el-bg-color);
}
.tiny-flow-container {
  height: calc(100vh - 150px);
  width: 100%;
}
</style>
