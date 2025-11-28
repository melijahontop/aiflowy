<script setup lang="ts">
import { cn } from '@aiflowy/utils';

import { ElEmpty } from 'element-plus';

interface RunResultProps {
  class?: string;
}

defineOptions({ name: 'RunResult' });
const props = defineProps<RunResultProps>();
</script>

<template>
  <div
    :class="
      cn('flex flex-1 flex-col gap-6 rounded-lg bg-white p-5', props.class)
    "
  >
    <h1 class="text-base font-medium text-[#1A1A1A]">运行结果</h1>
    <div class="flex-1 rounded-lg border border-[#F0F0F0] bg-[#F7F7F7] p-4">
      <ElEmpty />
    </div>
  </div>
</template>
