<script setup lang="ts">
import { ref } from 'vue';

import { IconifyIcon } from '@aiflowy/icons';
import { cn } from '@aiflowy/utils';

import { Plus } from '@element-plus/icons-vue';
import {
  ElButton,
  ElContainer,
  ElHeader,
  ElMain,
  ElPagination,
  ElProgress,
  ElSpace,
} from 'element-plus';

import Grid from './grid.vue';
import List from './list.vue';

type ViewType = 'grid' | 'list';
export interface Asset {
  id: number;
  name: string;
  source: string;
  type: string;
  size: string;
  lastUpdateTime: string;
}

const viewType = ref<ViewType>('list');
const assetList: Asset[] = [
  {
    id: 0,
    name: '29622d87bb474d21ca5562.docx',
    source: '自动生成',
    type: '文档',
    size: '1.5M',
    lastUpdateTime: '2025-06-19 11:10:00',
  },
  {
    id: 1,
    name: '29622d87bb474d21ca5562.docx',
    source: '自动生成',
    type: '文档',
    size: '1.5M',
    lastUpdateTime: '2025-06-19 11:10:00',
  },
  {
    id: 2,
    name: '29622d87bb474d21ca5562.docx',
    source: '自动生成',
    type: '文档',
    size: '1.5M',
    lastUpdateTime: '2025-06-19 11:10:00',
  },
  {
    id: 3,
    name: '29622d87bb474d21ca5562.docx',
    source: '自动生成',
    type: '文档',
    size: '1.5M',
    lastUpdateTime: '2025-06-19 11:10:00',
  },
];
</script>

<template>
  <ElContainer class="h-full bg-white">
    <ElHeader class="flex flex-col gap-6 !p-8 !pb-0" height="auto">
      <ElSpace :size="24">
        <h1 class="text-2xl font-medium text-[#333333]">素材库</h1>
        <ElSpace
          class="rounded-lg border border-[#E6E9EE] bg-[#F8FBFE] px-3.5 py-2.5"
        >
          <span class="text-sm font-medium text-[#969799]">
            <span class="text-[#1A1A1A]">256G</span> / 1T
          </span>
          <ElProgress
            class="w-[132px]"
            :percentage="20"
            :stroke-width="4"
            :show-text="false"
          />
        </ElSpace>
      </ElSpace>
      <div class="flex w-full items-center justify-between">
        <ElSpace class="text-2xl text-[#969799]">
          <button
            type="button"
            :class="
              cn(
                'rounded-sm border border-[#F0F0F0] bg-white p-1 hover:text-[#0066FF] active:text-[#0066FF]/50',
                viewType === 'list' && 'text-[#0066FF]',
              )
            "
            @click="viewType = 'list'"
          >
            <IconifyIcon icon="mdi:format-list-bulleted" />
          </button>
          <button
            type="button"
            :class="
              cn(
                'rounded-sm border border-[#F0F0F0] bg-white p-1 hover:text-[#0066FF] active:text-[#0066FF]/50',
                viewType === 'grid' && 'text-[#0066FF]',
              )
            "
            @click="viewType = 'grid'"
          >
            <IconifyIcon icon="mdi:view-grid-outline" />
          </button>
        </ElSpace>
        <ElButton type="primary" :icon="Plus">本地上传</ElButton>
      </div>
    </ElHeader>
    <ElMain class="!px-8 !py-6">
      <div class="flex flex-col items-center gap-5">
        <List v-show="viewType === 'list'" :data="assetList" />
        <Grid v-show="viewType === 'grid'" :data="assetList" />
        <ElPagination
          :page-sizes="[10, 20, 30, 50, 100]"
          :background="false"
          layout="total, sizes, prev, pager, next, jumper"
          :total="400"
        />
      </div>
    </ElMain>
  </ElContainer>
</template>
