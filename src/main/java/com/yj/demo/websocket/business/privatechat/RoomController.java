package com.yj.demo.websocket.business.privatechat;

import com.yj.demo.websocket.domain.Room;
import com.yj.demo.websocket.framework.websocket.handler.PrivateRoomHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController
{
    private final PrivateRoomHandler privateRoomHandler;

    @RequestMapping(value = "/room", method = RequestMethod.GET)
    public List<Room> getRoomList()
    {
        return privateRoomHandler.findAllRooms();
    }

    @RequestMapping(value = "/room", method = RequestMethod.POST)
    public Room postRoom(@RequestBody String name)
    {
        return privateRoomHandler.createRoomReturn(name);
    }

    @RequestMapping(value = "/room/{roomId}", method = RequestMethod.GET)
    public Room getRoom(@PathVariable String roomId)
    {
        return privateRoomHandler.findRoomById(roomId);
    }
}
