<script setup lang="ts">
import type { CheckboxValueType } from 'element-plus';

import type { Asset } from './index.vue';

import { ref } from 'vue';

import { cn } from '@aiflowy/utils';

import { ElCheckbox, ElCheckboxGroup, ElSpace, ElTag } from 'element-plus';

import docxIcon from '#/assets/assetLibrary/docx.webp';
import { Card, CardContent, CardTitle } from '#/components/card';

interface GridProps {
  data: Asset[];
}

const props = defineProps<GridProps>();
const checkAll = ref(false);
const isIndeterminate = ref(false);
const checkedIds = ref<number[]>([]);

const handleCheckAllChange = (val: CheckboxValueType) => {
  checkedIds.value = val ? props.data.map((asset) => asset.id) : [];
  isIndeterminate.value = false;
};
const handleCheckedIdsChange = (value: CheckboxValueType[]) => {
  const checkedCount = value.length;
  checkAll.value = checkedCount === props.data.length;
  isIndeterminate.value = checkedCount > 0 && checkedCount < props.data.length;
};
</script>

<template>
  <div class="flex w-full flex-col items-start gap-6">
    <ElCheckbox
      v-model="checkAll"
      :indeterminate="isIndeterminate"
      @change="handleCheckAllChange"
    >
      全选
    </ElCheckbox>
    <ElCheckboxGroup
      class="flex w-full flex-wrap items-center gap-5"
      v-model="checkedIds"
      @change="handleCheckedIdsChange"
    >
      <Card
        class="group relative max-w-[378px] flex-col gap-3 border border-[#F0F0F0] p-3 transition hover:-translate-y-2 hover:shadow-xl"
        v-for="asset in props.data"
        :key="asset.id"
      >
        <div
          class="flex h-[174px] w-full items-center justify-center rounded-lg bg-[#F9F8F8]"
        >
          <img class="shrink-0 object-cover" :src="docxIcon" />
        </div>
        <CardContent class="w-full gap-3">
          <CardTitle class="font-medium text-[#1A1A1A]">
            {{ asset.name }}
          </CardTitle>
          <div class="flex items-center justify-between">
            <ElSpace :size="10">
              <ElTag type="primary">{{ asset.source }}</ElTag>
              <ElTag color="#E6F9FF" style="--el-tag-text-color: #0099cc">
                {{ asset.type }}
              </ElTag>
            </ElSpace>
            <span class="text-xs text-[#969799]">{{ asset.size }}</span>
          </div>
        </CardContent>
        <div
          :class="
            cn(
              'absolute left-2.5 top-2.5 group-hover:block',
              !checkedIds.includes(asset.id) && 'hidden',
            )
          "
        >
          <ElCheckbox style="--el-checkbox-height: 1" :value="asset.id" />
        </div>
      </Card>
    </ElCheckboxGroup>
  </div>
</template>
