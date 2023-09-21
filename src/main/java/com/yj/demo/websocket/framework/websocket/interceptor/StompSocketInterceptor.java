package com.yj.demo.websocket.framework.websocket.interceptor;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * Stomp 소캣 접속 Interceptor
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class StompSocketInterceptor implements HandshakeInterceptor
{
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
        Map<String, Object> attributes) throws Exception
    {

        /*
         * set session attributes
         */
        List<NameValuePair> params =
            URLEncodedUtils.parse(Objects.requireNonNull(request.getURI()), StandardCharsets.UTF_8);
        for (NameValuePair pair : params)
        {
            attributes.put(pair.getName(), pair.getValue());
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
        Exception exception)
    {
        // do noting..
    }
}
