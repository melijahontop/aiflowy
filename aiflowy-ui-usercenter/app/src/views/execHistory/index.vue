<script setup lang="ts">
import { cn } from '@aiflowy/utils';

import { Delete, View } from '@element-plus/icons-vue';
import {
  ElButton,
  ElContainer,
  ElDatePicker,
  ElHeader,
  ElMain,
  ElPagination,
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

const listTitles = ['任务名称', '启动时间', '耗时', '状态', '智能体', '操作'];
const execHistoryList = [
  {
    id: 0,
    title: '口播数字人生成',
    startTime: '2025-09-09 12:00',
    duration: '10s',
    status: 'success',
    bot: '口播数字人生成',
  },
  {
    id: 1,
    title: '口播数字人生成',
    startTime: '2025-09-09 12:00',
    duration: '10s',
    status: 'fail',
    bot: '口播数字人生成',
  },
  {
    id: 2,
    title: '口播数字人生成',
    startTime: '2025-09-09 12:00',
    duration: '10s',
    status: 'pending',
    bot: '口播数字人生成',
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
    <ElMain class="!px-8">
      <ElContainer>
        <ElHeader
          class="grid grid-cols-6 place-items-center bg-[#F9FAFE] !p-0"
          height="54px"
        >
          <span
            class="text-sm text-[#1A1A1A]"
            v-for="title in listTitles"
            :key="title"
          >
            {{ title }}
          </span>
        </ElHeader>
        <ElMain class="!p-0">
          <div class="flex flex-col items-center gap-5">
            <div class="w-full">
              <div
                class="grid h-[60px] grid-cols-6 place-items-center bg-white text-sm text-[#333333]"
                v-for="record in execHistoryList"
                :key="record.id"
              >
                <span>{{ record.title }}</span>
                <span>{{ record.startTime }}</span>
                <span>{{ record.duration }}</span>
                <span
                  :class="
                    cn(
                      'rounded bg-[#E7F1FF] px-2.5 py-1 text-xs text-[#0066FF]',
                      record.status === 'success' &&
                        'bg-[#E7FBF9] text-[#00B8A9]',
                      record.status === 'fail' && 'bg-[#FFE8E8] text-[#FF4D4D]',
                    )
                  "
                >
                  {{
                    record.status === 'success'
                      ? '成功'
                      : record.status === 'fail'
                        ? '失败'
                        : '进行中'
                  }}
                </span>
                <span>{{ record.bot }}</span>
                <ElSpace :size="10">
                  <RouterLink :to="`/execHistory/${record.id}`">
                    <ElButton type="primary" :icon="View" link>
                      查看详情
                    </ElButton>
                  </RouterLink>
                  <ElButton type="danger" :icon="Delete" link>删除</ElButton>
                </ElSpace>
              </div>
            </div>
            <ElPagination
              :page-sizes="[10, 20, 30, 50, 100]"
              :background="false"
              layout="total, sizes, prev, pager, next, jumper"
              :total="400"
            />
          </div>
        </ElMain>
      </ElContainer>
    </ElMain>
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
