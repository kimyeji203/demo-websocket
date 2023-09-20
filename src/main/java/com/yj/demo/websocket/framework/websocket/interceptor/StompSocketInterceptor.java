package com.yj.demo.websocket.framework.websocket.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

// TODO 2023.09.20 여기부터 작업하기. -> 세션 유저명 작업하려고 한다~~
@Slf4j
@RequiredArgsConstructor
@Configuration
public class StompSocketInterceptor implements HandshakeInterceptor
{
    @Override
    public boolean beforeHandshake (ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception
    {
        return false;
    }

    @Override
    public void afterHandshake (ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception)
    {

    }
}
