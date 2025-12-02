<script setup lang="ts">
import { ElCol, ElRow } from 'element-plus';

import Preview from './preview.vue';
import Prompt from './prompt.vue';
</script>

<template>
  <ElRow class="h-full p-4" :gutter="16">
    <ElCol :span="8">
      <Prompt />
    </ElCol>
    <ElCol :span="8" />
    <ElCol :span="8">
      <Preview />
    </ElCol>
  </ElRow>
</template>
