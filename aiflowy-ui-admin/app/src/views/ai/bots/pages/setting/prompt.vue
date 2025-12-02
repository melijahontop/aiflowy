<script setup lang="ts">
import { ElIcon } from 'element-plus';

import MagicStaffIcon from '#/components/icons/MagicStaffIcon.vue';
</script>

<template>
  <div class="flex h-full flex-col gap-2 rounded-lg bg-white p-3">
    <div class="flex justify-between">
      <h1 class="text-base font-medium text-[#1A1A1A]">
        系统提示词（System Prompt）
      </h1>
      <button
        type="button"
        class="flex items-center gap-0.5 rounded-lg bg-[#F7F7F7] px-3 py-1"
      >
        <ElIcon size="16"><MagicStaffIcon /></ElIcon>
        <span
          class="bg-[linear-gradient(106.75666073298856deg,#F17E47,#D85ABF,#717AFF)] bg-clip-text text-sm text-transparent"
        >
          AI优化
        </span>
      </button>
    </div>
    <div class="flex-1 rounded-lg bg-[#F7F7F7] p-3">
      <span class="text-sm text-[#1A1A1A]">
        你是一个AI助手，请根据用户的问题给出清
      </span>
    </div>
  </div>
</template>
