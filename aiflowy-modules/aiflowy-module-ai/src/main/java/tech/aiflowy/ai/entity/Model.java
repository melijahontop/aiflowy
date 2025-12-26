
package tech.aiflowy.ai.entity;

import com.agentsflex.core.model.chat.ChatModel;
import com.agentsflex.core.model.embedding.EmbeddingModel;
import com.agentsflex.core.model.rerank.RerankModel;
import com.agentsflex.embedding.ollama.OllamaEmbeddingConfig;
import com.agentsflex.embedding.ollama.OllamaEmbeddingModel;
import com.agentsflex.embedding.openai.OpenAIEmbeddingConfig;
import com.agentsflex.embedding.openai.OpenAIEmbeddingModel;
import com.agentsflex.llm.deepseek.DeepseekChatModel;
import com.agentsflex.llm.deepseek.DeepseekConfig;
import com.agentsflex.llm.ollama.OllamaChatConfig;
import com.agentsflex.llm.ollama.OllamaChatModel;
import com.agentsflex.llm.openai.OpenAIChatConfig;
import com.agentsflex.llm.openai.OpenAIChatModel;
import com.agentsflex.rerank.DefaultRerankModel;
import com.agentsflex.rerank.DefaultRerankModelConfig;
import com.agentsflex.rerank.gitee.GiteeRerankModel;
import com.agentsflex.rerank.gitee.GiteeRerankModelConfig;
import com.mybatisflex.annotation.RelationManyToOne;
import com.mybatisflex.annotation.Table;
import tech.aiflowy.ai.entity.base.ModelBase;
import tech.aiflowy.common.util.StringUtil;

/**
 * 实体类。
 *
 * @author michael
 * @since 2024-08-23
 */

@Table("tb_model")
public class Model extends ModelBase {

    @RelationManyToOne(selfField = "providerId", targetField = "id")
    private ModelProvider modelProvider;

    /**
     * 模型类型
     */
    public final static String[] MODEL_TYPES = {"chatModel", "embeddingModel", "rerankModel"};


    public ModelProvider getModelProvider() {
        return modelProvider;
    }

    public void setModelProvider(ModelProvider modelProvider) {
        this.modelProvider = modelProvider;
    }

    public ChatModel toChatModel() {
        String providerType = modelProvider.getProviderType();
        if (StringUtil.noText(providerType)) {
            return null;
        }
        switch (providerType.toLowerCase()) {
            case "ollama":
                return ollamaLlm();
            case "deepseek":
                return deepSeekLLm();
            default:
                return openaiLLm();
        }
    }

    private ChatModel ollamaLlm() {
        OllamaChatConfig ollamaChatConfig = new OllamaChatConfig();
        ollamaChatConfig.setEndpoint(getEndpoint());
        ollamaChatConfig.setApiKey(getApiKey());
        ollamaChatConfig.setModel(getModelName());
        return new OllamaChatModel(ollamaChatConfig);
    }

    private ChatModel deepSeekLLm() {
        DeepseekConfig deepseekConfig = new DeepseekConfig();
        deepseekConfig.setProvider(getModelProvider().getProviderType());
        deepseekConfig.setEndpoint(getEndpoint());
        deepseekConfig.setApiKey(getApiKey());
        deepseekConfig.setModel(getModelName());
        deepseekConfig.setRequestPath(getRequestPath());
        return new DeepseekChatModel(deepseekConfig);
    }

    private ChatModel openaiLLm() {
        OpenAIChatConfig openAIChatConfig = new OpenAIChatConfig();
        openAIChatConfig.setProvider(getModelProvider().getProviderType());
        openAIChatConfig.setEndpoint(getEndpoint());
        openAIChatConfig.setApiKey(getApiKey());
        openAIChatConfig.setModel(getModelName());
        openAIChatConfig.setRequestPath(getRequestPath());
        return new OpenAIChatModel(openAIChatConfig);
    }

    public RerankModel toRerankModel() {
        switch (modelProvider.getProviderType().toLowerCase()) {
            case "gitee":
                GiteeRerankModelConfig giteeRerankModelConfig = new GiteeRerankModelConfig();
                giteeRerankModelConfig.setApiKey(getApiKey());
                giteeRerankModelConfig.setEndpoint(getEndpoint());
                giteeRerankModelConfig.setModel(getModelName());
                giteeRerankModelConfig.setRequestPath(getRequestPath());
                return new GiteeRerankModel(giteeRerankModelConfig);
            default:
                DefaultRerankModelConfig defaultRerankModelConfig = new DefaultRerankModelConfig();
                defaultRerankModelConfig.setApiKey(getApiKey());
                defaultRerankModelConfig.setEndpoint(getEndpoint());
                defaultRerankModelConfig.setRequestPath(getRequestPath());
                defaultRerankModelConfig.setModel(getModelName());
                return new DefaultRerankModel(defaultRerankModelConfig);
        }
    }

    public EmbeddingModel toEmbeddingModel() {
        String providerType = modelProvider.getProviderType();
        if (StringUtil.noText(providerType)) {
            return null;
        }
        switch (providerType.toLowerCase()) {
            case "ollama":
                OllamaEmbeddingConfig ollamaEmbeddingConfig = new OllamaEmbeddingConfig();
                ollamaEmbeddingConfig.setEndpoint(getEndpoint());
                ollamaEmbeddingConfig.setApiKey(getApiKey());
                ollamaEmbeddingConfig.setModel(getModelName());
                ollamaEmbeddingConfig.setRequestPath(getRequestPath());
                return new OllamaEmbeddingModel(ollamaEmbeddingConfig);
            default:
                OpenAIEmbeddingConfig openAIEmbeddingConfig = new OpenAIEmbeddingConfig();
                openAIEmbeddingConfig.setEndpoint(getEndpoint());
                openAIEmbeddingConfig.setApiKey(getApiKey());
                openAIEmbeddingConfig.setModel(getModelName());
                openAIEmbeddingConfig.setRequestPath(getRequestPath());
                return new OpenAIEmbeddingModel(openAIEmbeddingConfig);
        }
    }


}
