package com.yj.demo.websocket.business.privatechat;

import com.yj.demo.websocket.framework.websocket.domain.StompRoom;
import com.yj.demo.websocket.framework.websocket.handler.StompRoomHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController
{
    private final StompRoomHandler stompRoomHandler;

    @RequestMapping(value = "/room", method = RequestMethod.GET)
    public List<StompRoom> getRoomList()
    {
        return stompRoomHandler.findAllRooms();
    }

    @RequestMapping(value = "/room", method = RequestMethod.POST)
    public StompRoom postRoom(@RequestBody String name)
    {
        return stompRoomHandler.createRoomReturn(name);
    }

    @RequestMapping(value = "/room/{roomId}", method = RequestMethod.GET)
    public StompRoom getRoom(@PathVariable String roomId)
    {
        return stompRoomHandler.findRoomById(roomId);
    }
}
