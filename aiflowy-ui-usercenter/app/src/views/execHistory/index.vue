<script setup lang="ts">
import {
  ElContainer,
  ElDatePicker,
  ElHeader,
  ElMain,
  ElSelect,
  ElSpace,
} from 'element-plus';

const options = [
  {
    value: 'Option1',
    label: 'Option1',
  },
  {
    value: 'Option2',
    label: 'Option2',
  },
  {
    value: 'Option3',
    label: 'Option3',
  },
  {
    value: 'Option4',
    label: 'Option4',
  },
  {
    value: 'Option5',
    label: 'Option5',
  },
];
</script>

<template>
  <ElContainer class="h-full bg-[linear-gradient(153deg,white,#EFF8FF)]">
    <ElHeader class="!h-auto !p-8 !pb-0">
      <ElSpace direction="vertical" :size="24" alignment="flex-start">
        <h1 class="text-2xl font-medium text-[#333333]">执行记录</h1>
        <div class="flex items-center gap-8">
          <div class="flex items-center gap-4">
            <span class="text-nowrap text-sm text-[#1A1A1A]">执行状态</span>
            <ElSelect :options="options" placeholder="请选择执行状态" />
          </div>
          <div class="flex items-center gap-4">
            <span class="text-nowrap text-sm text-[#1A1A1A]">智能体</span>
            <ElSelect
              class="bot-select"
              :options="options"
              placeholder="请选择智能体"
            />
          </div>
          <div class="flex items-center gap-4">
            <span class="text-nowrap text-sm text-[#1A1A1A]">筛选时间</span>
            <ElDatePicker
              type="daterange"
              start-placeholder="选择开始日期"
              end-placeholder="选择结束日期"
              :default-value="[new Date(2010, 9, 1), new Date(2010, 10, 1)]"
            />
          </div>
        </div>
      </ElSpace>
    </ElHeader>
    <ElMain class="!px-8" />
  </ElContainer>
</template>

<style lang="css" scoped>
.el-select {
  --el-select-width: 165px;
}

.el-select.bot-select {
  --el-select-width: 343px;
}

.el-select :deep(.el-select__wrapper) {
  --el-fill-color-blank: #f6f6f6;
  --el-border-radius-base: 8px;
}

:deep(.el-date-editor) {
  --el-input-bg-color: #f6f6f6;
}
</style>
