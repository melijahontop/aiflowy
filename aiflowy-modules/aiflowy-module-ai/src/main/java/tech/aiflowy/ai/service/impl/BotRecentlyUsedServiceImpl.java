package tech.aiflowy.ai.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.aiflowy.ai.entity.BotRecentlyUsed;
import tech.aiflowy.ai.mapper.BotRecentlyUsedMapper;
import tech.aiflowy.ai.service.BotRecentlyUsedService;

/**
 * 最近使用 服务层实现。
 *
 * @author ArkLight
 * @since 2025-12-18
 */
@Service
public class BotRecentlyUsedServiceImpl extends ServiceImpl<BotRecentlyUsedMapper, BotRecentlyUsed>  implements BotRecentlyUsedService {

}
