package com.yj.demo.websocket.config;

import com.yj.demo.websocket.framework.websocket.handler.SimpleTextWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * {@link org.springframework.web.socket.config.annotation.WebSocketConfigurer}를 통한 웹소켓 설정
 *
 * @author yjkim
 */
@EnableWebSocket
@Configuration
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer
{
    private final SimpleTextWebSocketHandler simpleTextWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
    {
        registry.addHandler(simpleTextWebSocketHandler, "/simpleChat").setAllowedOrigins("*");
    }
}
