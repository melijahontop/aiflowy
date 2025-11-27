<script setup lang="ts">
import { cn } from '@aiflowy/utils';

interface CardDescriptionProps {
  class?: string;
}

defineOptions({ name: 'CardDescription' });
const props = defineProps<CardDescriptionProps>();
</script>

<template>
  <span
    :class="
      cn('overflow-hidden text-ellipsis text-xs text-[#ABB0B9]', props.class)
    "
  >
    <slot></slot>
  </span>
</template>
