package tech.aiflowy.ai.socket.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class ChatVoiceHandlerWebSocketInterceptor implements HandshakeInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ChatVoiceHandlerWebSocketInterceptor.class);


    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        logger.info("进入 ChatVoiceHandlerWebSocketInterceptor -> dev beforeHandshake");

        String sessionId = UriComponentsBuilder.fromUri(request.getURI()).build().getQueryParams().getFirst("sessionId");

        if (!StringUtils.hasLength(sessionId)) {
            logger.error("ChatVoiceHandlerWebSocketInterceptor  ------------->   sessionId 为空");
            return false;
        }

        attributes.put("sessionId", sessionId);

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
