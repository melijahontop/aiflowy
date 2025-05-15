<script setup lang="ts">
import DefaultTheme from 'vitepress/theme'
import {onMounted, nextTick, watch} from 'vue';
import {useRoute} from 'vitepress'
import mediumZoom from "medium-zoom";

const route = useRoute()
const initZoom = () => {
  mediumZoom('.main img', {background: 'var(--vp-c-bg)'})
};
onMounted(() => {
  initZoom()
})
watch(
    () => route.path,
    () => nextTick(() => {
      initZoom()
    })
)
</script>

<template>
  <DefaultTheme.Layout/>
</template>
