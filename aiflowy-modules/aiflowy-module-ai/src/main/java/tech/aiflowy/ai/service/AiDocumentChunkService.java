package tech.aiflowy.ai.service;

import tech.aiflowy.ai.entity.AiDocumentChunk;
import com.mybatisflex.core.service.IService;
import tech.aiflowy.ai.entity.AiKnowledge;

import java.math.BigInteger;

/**
 *  服务层。
 *
 * @author michael
 * @since 2024-08-23
 */
public interface AiDocumentChunkService extends IService<AiDocumentChunk> {

    boolean removeChunk(AiKnowledge knowledge, BigInteger chunkId);
}
