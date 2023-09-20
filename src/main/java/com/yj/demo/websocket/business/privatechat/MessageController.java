package com.yj.demo.websocket.business.privatechat;

import com.yj.demo.websocket.framework.websocket.WebSocketConst.MESSAGE_TYPES;
import com.yj.demo.websocket.framework.websocket.domain.StompMessage;
import com.yj.demo.websocket.framework.websocket.handler.StompRoomHandler;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

@RestController
@RequiredArgsConstructor
public class MessageController
{
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final StompRoomHandler stompRoomHandler;

    @MessageMapping("/chat/message")
    public void chatMessage(StompMessage message, SimpMessageHeaderAccessor headerAccessor)
    {
        String roomId = message.getRoomId();
        MESSAGE_TYPES type = message.getType();
        String writerName = message.getWriterName();


        if (type == MESSAGE_TYPES.OPEN)
        {
            stompRoomHandler.addSessionByRoomId(roomId, headerAccessor);

            message.setContext(StringUtils.join(writerName, "님 입장"));
            simpMessagingTemplate.convertAndSend("/sub/chat/room/" + roomId, message);
        } else if (type == MESSAGE_TYPES.CLOSE)
        {
            stompRoomHandler.removeSessionByRoomId(roomId, headerAccessor);

            message.setContext(StringUtils.join(writerName, "님 퇴장"));
            simpMessagingTemplate.convertAndSend("/sub/chat/room/" + roomId, message);
        } else
        {
            message.setType(MESSAGE_TYPES.TEXT);
            message.setContext(StringUtils.join("[" + writerName + "] ", message.getContext()));
            simpMessagingTemplate.convertAndSend("/sub/chat/room/" + roomId, message);
        }
    }
}
