package tech.aiflowy.ai.service.impl;

import tech.aiflowy.ai.entity.DocumentHistory;
import tech.aiflowy.ai.mapper.DocumentHistoryMapper;
import tech.aiflowy.ai.service.DocumentHistoryService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author michael
 * @since 2024-08-23
 */
@Service
public class DocumentHistoryServiceImpl extends ServiceImpl<DocumentHistoryMapper, DocumentHistory> implements DocumentHistoryService {

}
