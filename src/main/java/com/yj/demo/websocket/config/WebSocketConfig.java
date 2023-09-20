package com.yj.demo.websocket.config;

import com.yj.demo.websocket.framework.websocket.handler.PublicTextWebSocketHandler;
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
    private final PublicTextWebSocketHandler publicTextWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
    {
        registry.addHandler(publicTextWebSocketHandler, "/publicChat")
            // .setAllowedOrigins("*") // default : same origin
            .withSockJS()
            .setDisconnectDelay(60 * 1000) // default : 5sec
        ;
    }
}
