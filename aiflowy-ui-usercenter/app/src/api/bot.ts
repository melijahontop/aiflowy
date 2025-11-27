import type { BotInfo } from '@aiflowy/types';

import { requestClient } from '#/api/request';

/**
 * 获取所有智能体
 */
export async function getBotList() {
  return requestClient.get<BotInfo[]>('/api/v1/aiBot/list');
}

/**
 * 获取智能体信息
 */
export async function getBotDetail(id: string) {
  return requestClient.get<BotInfo>('/api/v1/aiBot/detail', {
    params: { id },
  });
}
