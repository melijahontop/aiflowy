<script setup lang="ts">
import { useRouter } from 'vue-router';

import { ArrowLeft } from '@element-plus/icons-vue';
import {
  ElContainer,
  ElDivider,
  ElHeader,
  ElIcon,
  ElMain,
  ElSpace,
} from 'element-plus';

import { RunResult, RunSteps } from '#/components/runBot';

const router = useRouter();
</script>

<template>
  <ElContainer class="h-full bg-white">
    <ElHeader class="!p-8 !pb-0" height="auto">
      <ElSpace class="cursor-pointer" :size="10" @click="router.back()">
        <ElIcon color="#969799" size="24"><ArrowLeft /></ElIcon>
        <h1 class="text-2xl font-medium text-[#333333]">口播数字人生成</h1>
      </ElSpace>
    </ElHeader>
    <ElMain class="items-center !px-8">
      <div
        class="flex h-full overflow-hidden rounded-xl border border-[#E6E9EE]"
      >
        <RunResult class="gap-5 p-6" />
        <ElDivider
          class="!m-0 !h-full !border-[#E6E9EE]"
          direction="vertical"
        />
        <RunSteps class="flex-1 gap-5 p-6" />
      </div>
    </ElMain>
  </ElContainer>
</template>
