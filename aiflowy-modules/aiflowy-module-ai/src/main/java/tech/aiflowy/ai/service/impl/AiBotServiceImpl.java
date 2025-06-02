package tech.aiflowy.ai.service.impl;

import tech.aiflowy.ai.entity.AiBot;
import tech.aiflowy.ai.mapper.AiBotMapper;
import tech.aiflowy.ai.service.AiBotService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.aiflowy.common.domain.Result;

/**
 *  服务层实现。
 *
 * @author michael
 * @since 2024-08-23
 */
@Service
public class AiBotServiceImpl extends ServiceImpl<AiBotMapper, AiBot> implements AiBotService {

    @Override
    public Result getDetail(String id) {
        AiBot aiBot = this.getMapper().selectOneById(id);
        return Result.success(aiBot);
    }
}
