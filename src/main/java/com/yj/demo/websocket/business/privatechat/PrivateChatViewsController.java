package com.yj.demo.websocket.business.privatechat;

import com.yj.demo.websocket.framework.websocket.handler.PrivateRoomHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 프라이빗 채팅 View  컨트롤러
 */
@Controller
@RequiredArgsConstructor
public class PrivateChatViewsController
{
    private final PrivateRoomHandler privateRoomHandler;

    /**
     * 채팅방 목록 화면
     *
     * @return
     */
    @GetMapping("/private/room")
    public String getPrivateRooms()
    {
        return "/private/private_rooms";
    }

    /**
     * 채팅방 채팅 화면
     *
     * @param roomId
     * @return
     */
    @GetMapping("/private/room/chat")
    public ModelAndView getPrivateRoomChat(@RequestParam("id") String roomId)
    {
        ModelAndView mv = new ModelAndView("/private/private_chat");
        mv.addObject("room", privateRoomHandler.findRoomById(roomId));
        return mv;
    }
}
