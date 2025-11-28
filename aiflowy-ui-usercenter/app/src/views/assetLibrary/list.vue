<script setup lang="ts">
import type { Asset } from './index.vue';

import { Delete, Download, EditPen, View } from '@element-plus/icons-vue';
import { ElButton, ElSpace, ElTable, ElTableColumn, ElTag } from 'element-plus';

import docxIcon from '#/assets/assetLibrary/docx.webp';

interface ListProps {
  data: Asset[];
}

const props = defineProps<ListProps>();
</script>

<template>
  <ElTable :data="props.data">
    <ElTableColumn type="selection" width="30" />
    <ElTableColumn label="文件名称" show-overflow-tooltip>
      <template #default="scope">
        <ElSpace :size="24">
          <img class="w-8 shrink-0 object-cover" :src="docxIcon" />
          <span>{{ scope.row.name }}</span>
        </ElSpace>
      </template>
    </ElTableColumn>
    <ElTableColumn label="文件来源" align="center">
      <template #default="scope">
        <ElTag type="primary">{{ scope.row.source }}</ElTag>
      </template>
    </ElTableColumn>
    <ElTableColumn label="文件类型" align="center">
      <template #default="scope">
        <ElTag color="#E6F9FF" style="--el-tag-text-color: #0099cc">
          {{ scope.row.type }}
        </ElTag>
      </template>
    </ElTableColumn>
    <ElTableColumn prop="size" label="文件大小" align="center" />
    <ElTableColumn prop="lastUpdateTime" label="修改时间" align="center" />
    <ElTableColumn label="操作" width="300" align="center">
      <template #default>
        <ElButton type="primary" :icon="View" link>预览</ElButton>
        <ElButton type="primary" :icon="Download" link>下载</ElButton>
        <ElButton type="primary" :icon="EditPen" link>编辑</ElButton>
        <ElButton type="danger" :icon="Delete" link>删除</ElButton>
      </template>
    </ElTableColumn>
  </ElTable>
</template>

<style lang="css" scoped>
.el-table {
  --el-table-text-color: #333333;
  --el-font-size-base: 12px;
  --el-table-header-text-color: #1a1a1a;
  --el-table-header-bg-color: #f9fafe;
  --el-table-border: none;
}

.el-table :deep(.el-table__inner-wrapper:before) {
  display: none;
}

.el-table :deep(thead),
.el-table :deep(tr) {
  height: 60px;
}

.el-table :deep(thead th) {
  font-weight: 400;
}
</style>
