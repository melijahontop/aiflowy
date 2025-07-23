package tech.aiflowy.ai.message.thirdPart;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.aiflowy.common.web.exceptions.BusinessException;

@Service
public class MessageHandlerService {

    private final Map<String, MessageHandler> handlerMap;

    private static final Logger log = LoggerFactory.getLogger(MessageHandlerService.class);

    @Autowired
    public MessageHandlerService(List<MessageHandler> handlers) {
        log.info("注入 messageHandlers");
        this.handlerMap = handlers.stream()
        .collect(Collectors.toMap(
            MessageHandler::getPlatformType,
            Function.identity()
        ));
    }

    /**
     * 根据平台类型获取对应的消息处理器
     */
    public MessageHandler getHandler(String platformType) {
        MessageHandler handler = handlerMap.get(platformType);
        if (handler == null) {
            log.error("不支持的平台类型: {}",platformType);
            throw new BusinessException("不支持的平台类型: " + platformType);
        }
        return handler;
    }

    /**
     * 处理消息
     */
    public Object handleMessage(String platformType, Object messageData, Map<String, Object> contextData) {
        MessageHandler handler = getHandler(platformType);
        return handler.handleMessage(messageData, contextData);
    }

}