package com.yj.demo.websocket.config;

import com.yj.demo.websocket.framework.websocket.interceptor.StompSocketInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Stomp 프로토콜 기반 웹소켓 설정
 * {@link WebSocketMessageBrokerConfigurer} 브로커 사용
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class StompSocketConfig implements WebSocketMessageBrokerConfigurer
{
    private final StompSocketInterceptor stompSocketInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry)
    {
        //@formatter:off
        registry.addEndpoint("/stomp/privateChat")
            .addInterceptors(stompSocketInterceptor)
            // .setAllowedOrigins("*") // default : same origin
            .setAllowedOriginPatterns("*")
            .withSockJS()
            .setDisconnectDelay(60 * 1000) // default : 5sec
        ;
        //@formatter:on
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry)
    {
        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableSimpleBroker("/sub");
    }
}
