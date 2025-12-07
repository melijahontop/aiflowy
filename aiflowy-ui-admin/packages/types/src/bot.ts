interface BotInfo {
  alias: string;
  anonymousEnabled: boolean;
  created: string;
  createdBy: number;
  deptId: number;
  description: string;
  icon: string;
  id: string;
  llmId: string;
  llmOptions: {
    maxMessageCount: number;
    maxReplyLength: number;
    temperature: number;
    topK: number;
    topP: number;
  };
  modified: string;
  modifiedBy: number;
  options: {
    anonymousEnabled: boolean;
    EncodingAESKey: string;
    presetQuestions: string[];
    reActModeEnabled: boolean;
    voiceEnabled: boolean;
    weChatMpAesKey: string;
    weChatMpAppId: string;
    weChatMpSecret: string;
    weChatMpToken: string;
    welcomeMessage: string;
  };
  tenantId: number;
  title: string;
}

interface Session {
  botId: string;
  sessionId: string;
  title: string;
}

interface ChatMessage {
  role: 'assistant' | 'user';
  created: number;
  updateAt: number;
  id: string;
  content: string;
  options?: {
    type: number;
    user_input: string;
  };
  placement: 'end' | 'start';
  noStyle: true;
  loading?: boolean;
  typing?: boolean;
  fileList?: File[];
  isFog?: boolean;
}

export type { BotInfo, ChatMessage, Session };
