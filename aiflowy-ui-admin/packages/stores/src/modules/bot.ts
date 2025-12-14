import { acceptHMRUpdate, defineStore } from 'pinia';

// 定义预设问题类型
export type PresetQuestionsType = {
  description: string;
  key: string;
};

// 仅包含预设问题的状态接口
interface BotStoreState {
  /** 预设问题列表 */
  presetQuestions: PresetQuestionsType[];
}

/**
 * @zh_CN 预设问题相关 store
 */
export const useBotStore = defineStore('bot-preset', {
  state: (): BotStoreState => ({
    // 初始化空数组
    presetQuestions: [],
  }),
  actions: {
    setPresetQuestions(questions: PresetQuestionsType[]) {
      this.presetQuestions = questions;
    },
  },
});

// 解决热更新问题
if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useBotStore, import.meta.hot));
}
