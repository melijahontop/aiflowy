package tech.aiflowy.ai.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.aiflowy.ai.entity.ModelProvider;
import tech.aiflowy.ai.mapper.ModelProviderMapper;
import tech.aiflowy.ai.service.ModelProviderService;

/**
 *  服务层实现。
 *
 * @author 12076
 * @since 2025-12-16
 */
@Service
public class ModelProviderServiceImpl extends ServiceImpl<ModelProviderMapper, ModelProvider>  implements ModelProviderService {

}
