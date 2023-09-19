package com.yj.demo.websocket.framework.websocket.handler;

import com.yj.demo.websocket.framework.utils.JsonUtil;
import com.yj.demo.websocket.framework.websocket.domain.SimpleMessage;
import com.yj.demo.websocket.framework.websocket.WebSocketConst.MESSAGE_TYPES;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SimpleTextWebSocketHandler extends TextWebSocketHandler
{
    private final static Map<String, WebSocketSession> SESSION_MAP = new HashMap<>();

    private final static String NAME = "name";
    private final static String LOG_INFO_FORMAT = "[WebSocket Handler] >>> {} : Session ID is {}";

    /**
     * session에서 key 추출
     *
     * @param session
     * @return
     * @throws AuthenticationException
     */
    private String getSessionId(WebSocketSession session) throws AuthenticationException
    {
        if (session == null || StringUtils.isBlank(session.getId()))
        {
            throw new AuthenticationException("Unknown web socket session..");
        }
        return session.getId();
    }

    /**
     * session query param 중 name 값 가져오기
     *
     * @param session
     * @return
     */
    private String getName(WebSocketSession session)
    {
        List<NameValuePair> params =
            URLEncodedUtils.parse(Objects.requireNonNull(session.getUri()), StandardCharsets.UTF_8);
        for (NameValuePair nv : params)
        {
            if (nv.getName().equals(NAME))
            {
                return nv.getValue();
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 전체 세션에 메지시 전파
     *
     * @param simpleMessage
     * @throws Exception
     */
    private void broadcastMessage(SimpleMessage simpleMessage) throws Exception
    {
        for (Entry e : SESSION_MAP.entrySet())
        {
            simpleMessage.setIsMine(e.getKey().equals(simpleMessage.getWriter()));
            ((WebSocketSession) e.getValue()).sendMessage(new TextMessage(JsonUtil.parseJsonObject(simpleMessage)));
        }
    }

    /**
     * socket connection open
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception
    {
        String key = this.getSessionId(session);
        SESSION_MAP.put(key, session);

        log.info(LOG_INFO_FORMAT, "Client connection open", key);
    }

    /**
     * socket message received
     *
     * @param session
     * @param textMessage
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception
    {
        String key = this.getSessionId(session);
        String payload = textMessage.getPayload();
        log.info(LOG_INFO_FORMAT, StringUtils.join("Received message payload >>> ", payload), key);

        /*
         * 메시지 분류
         */
        SimpleMessage simpleMessage = JsonUtil.parseString(payload, SimpleMessage.class);
        if (simpleMessage.getType() == null)
        {
            throw new RuntimeException("type 없음.");
        }
        String context = switch (simpleMessage.getType())
            {
                case OPEN -> StringUtils.join("[" + this.getName(session), "] 님 입장 (총 ", SESSION_MAP.size(), "명)");
                case TEXT -> StringUtils.isBlank(simpleMessage.getContext()) ?
                    StringUtils.EMPTY :
                    StringUtils.join("[" + this.getName(session), "] ", simpleMessage.getContext());
                default -> simpleMessage.getType().name();
            };
        simpleMessage.setWriter(key);
        simpleMessage.setContext(context);

        /*
         * 메시지 전파
         */
        this.broadcastMessage(simpleMessage);

        log.info(LOG_INFO_FORMAT, StringUtils.join("Send message payload >>> ", simpleMessage), key);
    }

    /**
     * socket connection close
     *
     * @param session
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception
    {
        String key = this.getSessionId(session);
        SESSION_MAP.remove(key);

        SimpleMessage simpleMessage = new SimpleMessage();
        simpleMessage.setType(MESSAGE_TYPES.CLOSE);
        simpleMessage.setWriter(key);
        simpleMessage.setContext(StringUtils.join(this.getName(session), " 님 퇴장 (총 ", SESSION_MAP.size(), "명)"));
        this.broadcastMessage(simpleMessage);

        log.info(LOG_INFO_FORMAT, "Client connection closed", key);
    }
}
