package tech.aiflowy.ai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import tech.aiflowy.ai.socket.handler.ChatVoiceHandler;
import tech.aiflowy.ai.socket.interceptor.ChatVoiceHandlerWebSocketInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig  implements WebSocketConfigurer{

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(new ChatVoiceHandler(),"/api/v1/aiBot/ws/chat").addInterceptors(new ChatVoiceHandlerWebSocketInterceptor()).setAllowedOriginPatterns("*");

    }
}
