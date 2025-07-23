
package tech.aiflowy.ai.message.thirdPart.wechat;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import tech.aiflowy.ai.message.thirdPart.MessageHandler;
import tech.aiflowy.ai.utils.MessageTypeConstants;

import java.util.Map;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import me.chanjar.weixin.mp.api.WxMpService;

@Component
public class WeChatMessageHandler implements MessageHandler {

    private static final Logger log = LoggerFactory.getLogger(WeChatMessageHandler.class);

    private static final List<String> SUPPORTED_MESSAGE_TYPES = Arrays.asList(
        MessageTypeConstants.MSG_TYPE_TEXT,
        MessageTypeConstants.MSG_TYPE_IMAGE,
        MessageTypeConstants.MSG_TYPE_VOICE,
        MessageTypeConstants.MSG_TYPE_EVENT
    );

    @Resource
    private WxMpService wxMpService;

    @Override
    public Object handleMessage(Object messageData, Map<String, Object> contextData) {

        log.info("messageData:{}", messageData);
        log.info("contextData:{}", contextData);

        // 提取信息
        HttpServletRequest request = (HttpServletRequest) contextData.getOrDefault("request", null);
        String apiKey = (String) contextData.getOrDefault("apiKey", "");
        String signature = ((String) contextData.getOrDefault("signature", ""));
        String openid = ((String) contextData.getOrDefault("openid", ""));
        String timestamp = ((String) contextData.getOrDefault("timestamp", ""));
        String nonce = ((String) contextData.getOrDefault("nonce", ""));
        String encryptType = ((String) contextData.getOrDefault("encryptType", ""));
        String msgSignature = ((String) contextData.getOrDefault("msgSignature", ""));

        // 校验签名
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            log.error("签名验证失败");
            return "";
        }

        WxMpXmlMessage inMessage = null;

        // 处理加密消息
        if (StringUtils.hasLength(encryptType) && "aes".equals(encryptType)) {
            inMessage = WxMpXmlMessage.fromEncryptedXml(messageData.toString(),
                wxMpService.getWxMpConfigStorage(), timestamp, nonce, msgSignature);
        } else {
            // 处理明文消息
            inMessage = WxMpXmlMessage.fromXml(messageData.toString());
        }

        WxMpXmlOutMessage outMessage = handle(inMessage, request);

        if (outMessage != null) {

            WxMpKefuMessage message = new WxMpKefuMessage();
            message.setMsgType(WxConsts.KefuMsgType.TEXT);
            message.setToUser(inMessage.getFromUser());
            message.setContent("欢迎欢迎，热烈欢迎\n换行测试\n超链接:<a href=\"http://www.baidu.com\">Hello World</a>");

            try {
                wxMpService.getKefuService().sendKefuMessage(message);
            } catch (WxErrorException e) {
                log.error("发送客服消息失败：{}",e.getMessage());
                // 如果是加密模式，需要加密回复消息
                if (StringUtils.hasLength(encryptType) && "aes".equals(encryptType)) {
                    return outMessage.toEncryptedXml(wxMpService.getWxMpConfigStorage());
                } else {
                    return outMessage.toXml();
                }
            }

        }

        return "";
    }

    @Override
    public String getPlatformType() {
        return MessageTypeConstants.PLATFORM_WECHAT;
    }

    @Override
    public boolean supportMessageType(String messageType) {
        return SUPPORTED_MESSAGE_TYPES.contains(messageType);
    }

    /**
     * 处理接收到的微信消息
     */
    public WxMpXmlOutMessage handle(WxMpXmlMessage inMessage, HttpServletRequest request) {

        String msgType = inMessage.getMsgType();
        String openId = inMessage.getFromUser();

        log.info("收到消息类型: {}, 来自用户: {}", msgType, openId);

        WxMpXmlOutMessage outMessage = null;

        try {
            switch (msgType) {
                case WxConsts.XmlMsgType.TEXT:
                    outMessage = handleTextMessage(inMessage);
                    break;
                case WxConsts.XmlMsgType.IMAGE:
                    outMessage = handleImageMessage(inMessage);
                    break;
                case WxConsts.XmlMsgType.VOICE:
                    outMessage = handleVoiceMessage(inMessage);
                    break;
                case WxConsts.XmlMsgType.EVENT:
                    outMessage = handleEventMessage(inMessage);
                    break;
                default:
                    outMessage = handleDefaultMessage(inMessage);
                    break;
            }
        } catch (Exception e) {
            log.error("处理消息异常", e);
            outMessage = createTextReplyMessage(inMessage, "系统繁忙，请稍后再试");
        }

        return outMessage;
    }

    /**
     * 处理文本消息
     */
    private WxMpXmlOutMessage handleTextMessage(WxMpXmlMessage inMessage) {
        String content = inMessage.getContent();
        log.info("收到文本消息: {}", content);

        String replyContent = "hello";

        return createTextReplyMessage(inMessage, replyContent);
    }

    /**
     * 处理图片消息
     */
    private WxMpXmlOutMessage handleImageMessage(WxMpXmlMessage inMessage) {
        log.info("收到图片消息, MediaId: {}", inMessage.getMediaId());
        return createTextReplyMessage(inMessage, "收到您发送的图片，我们正在处理中...");
    }

    /**
     * 处理语音消息
     */
    private WxMpXmlOutMessage handleVoiceMessage(WxMpXmlMessage inMessage) {
        log.info("收到语音消息, MediaId: {}", inMessage.getMediaId());
        return createTextReplyMessage(inMessage, "收到您的语音消息，我们正在分析中...");
    }

    /**
     * 处理事件消息
     */
    private WxMpXmlOutMessage handleEventMessage(WxMpXmlMessage inMessage) {
        String event = inMessage.getEvent();
        log.info("收到事件: {}", event);

        String replyContent = "接收事件";

        switch (event) {
            case WxConsts.EventType.SUBSCRIBE:
                break;
            case WxConsts.EventType.UNSUBSCRIBE:
                // 取消关注事件，微信不会推送回复消息
                return null;
            case WxConsts.EventType.CLICK:
                replyContent = handleMenuClick(inMessage);
                break;
            default:
        }

        return createTextReplyMessage(inMessage, replyContent);
    }

    /**
     * 处理菜单点击事件
     */
    private String handleMenuClick(WxMpXmlMessage inMessage) {
        String eventKey = inMessage.getEventKey();
        log.info("菜单点击事件Key: {}", eventKey);

        return "您点击了菜单：" + eventKey;
    }

    /**
     * 处理默认消息
     */
    private WxMpXmlOutMessage handleDefaultMessage(WxMpXmlMessage inMessage) {
        return createTextReplyMessage(inMessage, "感谢您的消息，我们已收到！");
    }

    /**
     * 创建文本回复消息
     */
    private WxMpXmlOutMessage createTextReplyMessage(WxMpXmlMessage inMessage, String content) {
        return WxMpXmlOutMessage.TEXT()
            .content(content)
            .fromUser(inMessage.getToUser())
            .toUser(inMessage.getFromUser())
            .build();
    }
}
