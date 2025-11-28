<script setup lang="ts">
import type { ServerSentEventMessage } from 'fetch-event-stream';

import { computed, ref, watch } from 'vue';

import { ElMessage } from 'element-plus';

import ShowJson from '#/components/json/ShowJson.vue';
import { $t } from '#/locales';

export interface ExecResultProps {
  workflowId: any;
  executeMessage: null | ServerSentEventMessage;
  nodeJson: any;
}
const props = defineProps<ExecResultProps>();

const finalNode = computed(() => {
  const nodes = props.nodeJson;
  if (nodes.length > 0) {
    let endNode = nodes[nodes.length - 1].original;
    for (const node of nodes) {
      if (node.original.type === 'endNode') {
        endNode = node.original;
      }
    }
    return endNode;
  }
  return null;
});
const result = ref('');
watch(
  () => props.executeMessage,
  (newMsg) => {
    if (newMsg && newMsg.data) {
      const msg = JSON.parse(newMsg.data).content;
      if (msg.execResult) {
        ElMessage.success($t('message.success'));
        result.value = msg.execResult;
      }
      if (msg.status === 'error') {
        ElMessage.error($t('message.fail'));
        result.value = msg;
      }
    }
  },
  { deep: true },
);
</script>

<template>
  <div>
    <div>
      <ShowJson :value="result" />
    </div>
  </div>
</template>

<style scoped></style>
