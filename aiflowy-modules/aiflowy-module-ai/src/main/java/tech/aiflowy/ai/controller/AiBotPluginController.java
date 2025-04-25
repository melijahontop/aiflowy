package tech.aiflowy.ai.controller;

import tech.aiflowy.common.domain.Result;
import tech.aiflowy.common.web.controller.BaseCurdController;
import tech.aiflowy.ai.entity.AiBotPlugin;
import tech.aiflowy.ai.service.AiBotPluginService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.aiflowy.common.entity.LoginAccount;

/**
 *  控制层。
 *
 * @author Administrator
 * @since 2025-04-25
 */
@RestController
@RequestMapping("/api/v1/aiBotPlugin")
public class AiBotPluginController extends BaseCurdController<AiBotPluginService, AiBotPlugin> {
    public AiBotPluginController(AiBotPluginService service) {
        super(service);
    }

    @Override
    protected Result onSaveOrUpdateBefore(AiBotPlugin entity, boolean isSave) {
//        LoginAccount loginUser = SaTokenUtil.getLoginAccount();
//        commonFiled(entity,loginUser.getId(),loginUser.getTenantId(), loginUser.getDeptId());
//
        return super.onSaveOrUpdateBefore(entity, isSave);
    }
}