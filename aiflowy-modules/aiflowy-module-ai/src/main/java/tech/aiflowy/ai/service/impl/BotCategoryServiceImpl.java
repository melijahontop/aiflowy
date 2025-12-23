package tech.aiflowy.ai.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.aiflowy.ai.entity.BotCategory;
import tech.aiflowy.ai.mapper.BotCategoryMapper;
import tech.aiflowy.ai.service.BotCategoryService;

/**
 * bot分类 服务层实现。
 *
 * @author ArkLight
 * @since 2025-12-18
 */
@Service
public class BotCategoryServiceImpl extends ServiceImpl<BotCategoryMapper, BotCategory>  implements BotCategoryService {

}
