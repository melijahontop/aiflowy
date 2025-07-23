
package tech.aiflowy.ai.controller;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.aiflowy.ai.message.thirdPart.MessageHandlerService;
import tech.aiflowy.ai.utils.MessageTypeConstants;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import cn.dev33.satoken.annotation.SaIgnore;

@RestController
@RequestMapping("/api/v1/message")
@SaIgnore
public class ThirdPartMessageController {

    private static final Logger log = LoggerFactory.getLogger(ThirdPartMessageController.class);

    @Resource
    private WxMpService wxMpService;

    @Resource
    private MessageHandlerService messageHandlerService;


    /**
     * 通用消息接收接口
     * 根据平台类型路由到不同的处理器
     */
    @PostMapping(value = "/{platform}")
    public Object receiveMessage(
        @PathVariable String platform,
        @RequestBody
        String requestBody,
        @RequestParam
        Map<String, String> params,
        HttpServletRequest request
    ) {

        log.info("接收{}平台消息", platform);
        log.info("请求参数: {}", params);

        try {
            // 构建上下文数据
            Map<String, Object> contextData = new HashMap<>();
            contextData.put("request", request);
            contextData.putAll(params);

            // 使用通用消息处理服务
            Object result = messageHandlerService.handleMessage(platform, requestBody, contextData);

            if (result != null) {
                return result;
            }

        } catch (Exception e) {
            log.error("处理{}平台消息异常", platform, e);
            
        }

        return "系统繁忙请稍后重试！";
    }

    /**
     * 微信签名验证接口（GET请求）
     */
    @GetMapping("/wechat")
    public String verifyWeChatSignature(@RequestParam("signature")
    String signature,
        @RequestParam("timestamp")
        String timestamp,
        @RequestParam("nonce")
        String nonce,
        @RequestParam("echostr")
        String echostr,
        @RequestParam ("apiKey") String apiKey
    ) {

        if (wxMpService == null) {
            log.error("微信服务未配置");
            return "";
        }

        log.info("微信签名验证: signature={}, timestamp={}, nonce={}, echostr={}",
            signature, timestamp, nonce, echostr);

        try {
            if (wxMpService.checkSignature(timestamp, nonce, signature)) {
                log.info("微信签名验证成功");
                return echostr;
            } else {
                log.error("微信签名验证失败");
                return "";
            }
        } catch (Exception e) {
            log.error("微信签名验证异常", e);
            return "";
        }
    }


}
