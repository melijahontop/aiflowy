<script setup lang="ts">
import { cn } from '@aiflowy/utils';

import { Avatar } from '@element-plus/icons-vue';
import { ElAvatar } from 'element-plus';

interface CardAvatarProps {
  class?: string;
  size?: number;
  src?: string;
  defaultAvatar?: string;
}

defineOptions({ name: 'CardAvatar' });
const props = defineProps<CardAvatarProps>();
</script>

<template>
  <template v-if="props.defaultAvatar">
    <ElAvatar
      :class="cn('shrink-0', props.class)"
      :size="props.size ?? 36"
      :src="props.src ?? props.defaultAvatar"
    />
  </template>
  <template v-else>
    <ElAvatar
      :class="cn('shrink-0', props.class)"
      :size="props.size ?? 36"
      :icon="Avatar"
    />
  </template>
</template>
