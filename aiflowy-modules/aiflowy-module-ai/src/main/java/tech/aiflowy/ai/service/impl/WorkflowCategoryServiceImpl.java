package tech.aiflowy.ai.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.aiflowy.ai.entity.WorkflowCategory;
import tech.aiflowy.ai.mapper.WorkflowCategoryMapper;
import tech.aiflowy.ai.service.WorkflowCategoryService;

/**
 *  服务层实现。
 *
 * @author ArkLight
 * @since 2025-12-11
 */
@Service
public class WorkflowCategoryServiceImpl extends ServiceImpl<WorkflowCategoryMapper, WorkflowCategory>  implements WorkflowCategoryService {

}
