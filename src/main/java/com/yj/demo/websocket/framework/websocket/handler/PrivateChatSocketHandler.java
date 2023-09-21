package com.yj.demo.websocket.framework.websocket.handler;

import com.yj.demo.websocket.framework.websocket.WebSocketConst;
import com.yj.demo.websocket.domain.RoomSocketMessage;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.WebSocketSession;

/**
 * 프라이빗 채팅 소캣 핸들러
 */
@Configuration
@RequiredArgsConstructor
public class PrivateChatSocketHandler
{
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final PrivateRoomHandler privateRoomHandler;

    private final static String TOPIC_PATH = "/sub/private/chat/room/";

    /**
     * 해당 메시지 작성자명 추출
     *
     * @param headerAccessor
     * @return
     */
    private String getWriterName(SimpMessageHeaderAccessor headerAccessor)
    {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if (sessionAttributes == null || sessionAttributes.isEmpty())
        {
            return StringUtils.EMPTY;
        }

        return sessionAttributes.containsKey(WebSocketConst.SESSION_ATTR_NAME) ?
            String.valueOf(sessionAttributes.get(WebSocketConst.SESSION_ATTR_NAME)) :
            StringUtils.EMPTY;
    }

    /**
     * 소켓 오픈 메시지 (입장)
     *
     * @param roomId
     * @param headerAccessor
     */
    public void sendOpen(String roomId, SimpMessageHeaderAccessor headerAccessor)
    {
        privateRoomHandler.addSessionByRoomId(roomId, headerAccessor);

        String writerName = this.getWriterName(headerAccessor);

        RoomSocketMessage message = new RoomSocketMessage();
        message.setWriterName(writerName);
        message.setRoomId(roomId);
        message.setType(WebSocketConst.MESSAGE_TYPES.OPEN);
        message.setContext(
            StringUtils.join(writerName, "님 입장 (총 ", privateRoomHandler.getSessionCntByRoomId(roomId), "명)"));

        simpMessagingTemplate.convertAndSend(TOPIC_PATH + roomId, message);
    }

    /**
     * 소켓 클로즈 메시지 (퇴장)
     *
     * @param roomId
     * @param headerAccessor
     */
    public void sendClose(String roomId, SimpMessageHeaderAccessor headerAccessor)
    {
        privateRoomHandler.removeSessionByRoomId(roomId, headerAccessor);

        String writerName = this.getWriterName(headerAccessor);

        RoomSocketMessage message = new RoomSocketMessage();
        message.setWriterName(writerName);
        message.setRoomId(roomId);
        message.setType(WebSocketConst.MESSAGE_TYPES.CLOSE);
        message.setContext(
            StringUtils.join(writerName, "님 퇴장 (총 ", privateRoomHandler.getSessionCntByRoomId(roomId), "명)"));

        simpMessagingTemplate.convertAndSend(TOPIC_PATH + roomId, message);
    }

    /**
     * 소켓 메시지 전송 (채팅)
     *
     * @param roomId
     * @param context
     * @param headerAccessor
     */
    public void sendText(String roomId, String context, SimpMessageHeaderAccessor headerAccessor)
    {
        String writerName = this.getWriterName(headerAccessor);

        RoomSocketMessage message = new RoomSocketMessage();
        message.setWriterKey(headerAccessor.getSessionId());
        message.setWriterName(writerName);
        message.setRoomId(roomId);
        message.setType(WebSocketConst.MESSAGE_TYPES.TEXT);
        message.setContext(StringUtils.join("[" + writerName + "] ", context));

        simpMessagingTemplate.convertAndSend(TOPIC_PATH + roomId, message);
    }
}
