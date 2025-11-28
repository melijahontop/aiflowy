<script setup lang="ts">
import type { BotInfo } from '@aiflowy/types';

import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { ArrowLeft } from '@element-plus/icons-vue';
import {
  ElAside,
  ElButton,
  ElContainer,
  ElHeader,
  ElIcon,
  ElMain,
  ElSpace,
} from 'element-plus';
import { tryit } from 'radash';

import { getBotDetail } from '#/api';
import defaultBotAvatar from '#/assets/defaultBotAvatar.png';
import {
  Card,
  CardAvatar,
  CardContent,
  CardDescription,
  CardTitle,
} from '#/components/card';
import { RunResult, RunSteps } from '#/components/runBot';

import Form from './form.vue';

const route = useRoute();
const router = useRouter();
const bot = ref<BotInfo>();

onMounted(async () => {
  if (route.params.id) {
    const [err, res] = await tryit(getBotDetail)(route.params.id as string);

    if (!err) {
      bot.value = res;
    }
  }
});
</script>

<template>
  <ElContainer class="h-full">
    <ElHeader class="!px-8 !py-4" height="fit-content">
      <div class="flex flex-col gap-6">
        <ElSpace class="cursor-pointer" :size="10" @click="router.back()">
          <ElIcon color="#969799" size="24"><ArrowLeft /></ElIcon>
          <h1 class="text-2xl font-medium text-[#333333]">{{ bot?.title }}</h1>
        </ElSpace>
        <div
          class="flex items-center justify-between rounded-lg bg-[linear-gradient(153deg,#D3E3FD,#CBDEFE)] px-10 py-9"
        >
          <Card class="max-w-none cursor-auto items-center gap-7">
            <CardAvatar
              :size="72"
              :src="bot?.icon"
              :default-avatar="defaultBotAvatar"
            />
            <CardContent class="gap-3">
              <CardTitle class="text-3xl font-medium text-[#1A1A1A]">
                {{ bot?.title }}
              </CardTitle>
              <CardDescription class="text-base text-[#5E6673]">
                {{ bot?.description }}
              </CardDescription>
            </CardContent>
          </Card>
          <RouterLink to="">
            <ElButton color="#0066FF" size="large" round plain>
              执行记录
            </ElButton>
          </RouterLink>
        </div>
      </div>
    </ElHeader>
    <ElMain class="!px-8 !pb-4 !pt-0">
      <ElContainer class="h-full gap-4">
        <ElAside
          width="366px"
          class="flex flex-col gap-6 rounded-lg bg-white p-5"
        >
          <h1 class="text-base font-medium text-[#1A1A1A]">输入参数</h1>
          <Form />
        </ElAside>
        <ElAside width="366px">
          <RunSteps />
        </ElAside>
        <RunResult />
      </ElContainer>
    </ElMain>
  </ElContainer>
</template>
