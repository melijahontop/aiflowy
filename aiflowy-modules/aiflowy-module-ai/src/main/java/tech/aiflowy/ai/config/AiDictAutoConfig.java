package tech.aiflowy.ai.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import tech.aiflowy.ai.mapper.BotCategoryMapper;
import tech.aiflowy.ai.mapper.WorkflowCategoryMapper;
import tech.aiflowy.ai.mapper.WorkflowMapper;
import tech.aiflowy.common.util.SpringContextUtil;
import tech.aiflowy.core.dict.DictManager;
import tech.aiflowy.core.dict.loader.DbDataLoader;

import javax.annotation.Resource;

@Configuration
public class AiDictAutoConfig {

    @Resource
    private WorkflowMapper workflowMapper;
    @Resource
    private WorkflowCategoryMapper workflowCategoryMapper;
    @Resource
    private BotCategoryMapper botCategoryMapper;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationStartup() {

        DictManager dictManager = SpringContextUtil.getBean(DictManager.class);
        dictManager.putLoader(new DbDataLoader<>("aiWorkFlow", workflowMapper, "id", "title", null, null, false));
        dictManager.putLoader(new DbDataLoader<>("aiWorkFlowCategory", workflowCategoryMapper, "id", "category_name", null, null, false));
        dictManager.putLoader(new DbDataLoader<>("aiBotCategory", botCategoryMapper, "id", "category_name", null, null, false));
    }
}
