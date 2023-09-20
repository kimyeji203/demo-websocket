package com.yj.demo.websocket.business.privatechat;

import com.yj.demo.websocket.framework.websocket.WebSocketConst.MESSAGE_TYPES;
import com.yj.demo.websocket.domain.RoomSocketMessage;
import com.yj.demo.websocket.framework.websocket.handler.PrivateChatSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PrivateChatController
{
    private final PrivateChatSocketHandler privateChatSocketHandler;

    @MessageMapping("/private/chat/message")
    public void chatMessage (RoomSocketMessage message, SimpMessageHeaderAccessor headerAccessor)
    {
        String roomId = message.getRoomId();
        MESSAGE_TYPES type = message.getType();
        String writerName = message.getWriterName();

        if (type == MESSAGE_TYPES.OPEN)
        {
            privateChatSocketHandler.sendOpen(roomId, headerAccessor, writerName);
        } else if (type == MESSAGE_TYPES.CLOSE)
        {
            privateChatSocketHandler.sendClose(roomId, headerAccessor, writerName);
        } else
        {
            privateChatSocketHandler.sendText(roomId, message.getContext(), writerName);
        }
    }
}
