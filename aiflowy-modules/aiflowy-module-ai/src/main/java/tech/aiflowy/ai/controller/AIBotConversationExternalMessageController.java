package tech.aiflowy.ai.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.alicp.jetcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.aiflowy.ai.entity.AiBotConversationMessage;
import tech.aiflowy.ai.entity.AiBotMessage;
import tech.aiflowy.ai.service.AiBotConversationMessageService;
import tech.aiflowy.common.domain.Result;
import tech.aiflowy.common.web.controller.BaseCurdController;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/conversation")
public class AIBotConversationExternalMessageController extends BaseCurdController<AiBotConversationMessageService, AiBotConversationMessage> {

    @Resource
    private AiBotConversationMessageService conversationMessageService;

    @Autowired
    @Qualifier("defaultCache") // 指定 Bean 名称
    private Cache<String, Object> cache;

    public AIBotConversationExternalMessageController(AiBotConversationMessageService service) {
        super(service);
    }

    @GetMapping("externalList")
    @SaIgnore
    public Result externalList(@RequestParam(value = "botId") BigInteger botId, @RequestParam(value = "tempUserId") String tempUserId) {
        boolean login = StpUtil.isLogin();
        if (login) {
            return conversationMessageService.externalList(botId);
        } else {
            List<AiBotConversationMessage> result = (List<AiBotConversationMessage>)cache.get(tempUserId + botId);
            System.out.println(result);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("cons", result);
            return Result.success(resultMap);
        }
    }

    @GetMapping("deleteConversation")
    public Result deleteConversation(@RequestParam(value = "botId") String botId,
                                     @RequestParam(value = "sessionId") String sessionId
    ) {

        return conversationMessageService.deleteConversation(botId, sessionId);
    }

    @GetMapping("updateConversation")
    public Result updateConversation(@RequestParam(value = "botId") String botId,
                                     @RequestParam(value = "sessionId") String sessionId,
                                     @RequestParam(value = "title") String title
    ) {

        return conversationMessageService.updateConversation(botId, sessionId, title);
    }
}
