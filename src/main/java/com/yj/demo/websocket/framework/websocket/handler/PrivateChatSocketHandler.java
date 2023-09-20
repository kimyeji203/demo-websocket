package com.yj.demo.websocket.framework.websocket.handler;

import com.yj.demo.websocket.framework.websocket.WebSocketConst;
import com.yj.demo.websocket.domain.RoomSocketMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
@RequiredArgsConstructor
public class PrivateChatSocketHandler
{
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final PrivateRoomHandler privateRoomHandler;

    private final static String TOPIC_PATH = "/sub/private/chat/room/";

    public void sendOpen(String roomId, SimpMessageHeaderAccessor headerAccessor, String writerName)
    {
        privateRoomHandler.addSessionByRoomId(roomId, headerAccessor);

        RoomSocketMessage message = new RoomSocketMessage();
        message.setRoomId(roomId);
        message.setType(WebSocketConst.MESSAGE_TYPES.OPEN);
        message.setContext(StringUtils.join(writerName, "님 입장 (총 ", privateRoomHandler.getSessionCntByRoomId(roomId),"명)"));

        simpMessagingTemplate.convertAndSend(TOPIC_PATH + roomId, message);
    }


    public void sendClose(String roomId, SimpMessageHeaderAccessor headerAccessor, String writerName)
    {
        privateRoomHandler.removeSessionByRoomId(roomId, headerAccessor);

        RoomSocketMessage message = new RoomSocketMessage();
        message.setRoomId(roomId);
        message.setType(WebSocketConst.MESSAGE_TYPES.CLOSE);
        message.setContext(StringUtils.join(writerName, "님 퇴장 (총 ", privateRoomHandler.getSessionCntByRoomId(roomId),"명)"));

        simpMessagingTemplate.convertAndSend(TOPIC_PATH + roomId, message);
    }


    public void sendText(String roomId,String context, String writerName)
    {
        RoomSocketMessage message = new RoomSocketMessage();
        message.setRoomId(roomId);
        message.setType(WebSocketConst.MESSAGE_TYPES.TEXT);
        message.setContext(StringUtils.join("["+writerName+"] ", context));

        simpMessagingTemplate.convertAndSend(TOPIC_PATH + roomId, message);
    }
}
