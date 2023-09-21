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

/**
 * Romm 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class RoomController
{
    private final PrivateRoomHandler privateRoomHandler;

    /**
     * 룸 목록 조회
     *
     * @return
     */
    @RequestMapping(value = "/room", method = RequestMethod.GET)
    public List<Room> getRoomList()
    {
        return privateRoomHandler.findAllRooms();
    }

    /**
     * 룸 등록
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/room", method = RequestMethod.POST)
    public Room postRoom(@RequestBody String name)
    {
        return privateRoomHandler.createRoomReturn(name);
    }

    /**
     * 룸 단건 조회
     *
     * @param roomId
     * @return
     */
    @RequestMapping(value = "/room/{roomId}", method = RequestMethod.GET)
    public Room getRoom(@PathVariable String roomId)
    {
        return privateRoomHandler.findRoomById(roomId);
    }
}
