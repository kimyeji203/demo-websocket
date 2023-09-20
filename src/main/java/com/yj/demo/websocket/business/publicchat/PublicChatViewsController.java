package com.yj.demo.websocket.business.publicchat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PublicChatViewsController
{
    /**
     * 전체 채팅방 화면
     *
     * @return
     */
    @GetMapping("/public/chat")
    public String getPublicChat()
    {
        return "public/public_chat";
    }
}
