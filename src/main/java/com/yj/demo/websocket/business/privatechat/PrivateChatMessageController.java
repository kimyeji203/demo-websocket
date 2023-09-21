package com.yj.demo.websocket.business.privatechat;

import com.yj.demo.websocket.domain.RoomSocketMessage;
import com.yj.demo.websocket.framework.websocket.WebSocketConst.MESSAGE_TYPES;
import com.yj.demo.websocket.framework.websocket.handler.PrivateChatSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

/**
 * 프라이빗 채팅 메시지 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class PrivateChatMessageController
{
    private final PrivateChatSocketHandler privateChatSocketHandler;

    /**
     * 웹소켓 메시지 받음
     *
     * @param message
     * @param headerAccessor
     */
    @MessageMapping("/private/chat/message")
    public void chatMessage(RoomSocketMessage message, SimpMessageHeaderAccessor headerAccessor)
    {
        String roomId = message.getRoomId();
        MESSAGE_TYPES type = message.getType();

        if (type == MESSAGE_TYPES.OPEN)
        {
            privateChatSocketHandler.sendOpen(roomId, headerAccessor);
        } else if (type == MESSAGE_TYPES.CLOSE)
        {
            privateChatSocketHandler.sendClose(roomId, headerAccessor);
        } else
        {
            privateChatSocketHandler.sendText(roomId, message.getContext(), headerAccessor);
        }
    }
}
