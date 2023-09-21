package com.yj.demo.websocket.framework.websocket.handler;

import com.yj.demo.websocket.domain.Room;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Repository;

/**
 * 프라이빗 채팅룸 핸들러
 */
@Repository
public class PrivateRoomHandler
{
    private final Map<String, Room> ROOM_MAP = new LinkedHashMap<>();

    /**
     * 채팅방 전체 목록 조회
     *
     * @return
     */
    public List<Room> findAllRooms()
    {
        return ROOM_MAP.values().stream().toList();
    }

    /**
     * ID로 채팅방 조회
     *
     * @param id
     * @return
     */
    public Room findRoomById(String id)
    {
        if (StringUtils.isBlank(id))
        {
            return null;
        }

        return ROOM_MAP.get(id);
    }

    /**
     * new 채팅방 생성
     *
     * @param name
     * @return
     */
    public Room createRoomReturn(String name)
    {
        if (StringUtils.isBlank(name))
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm-ss");
            name = simpleDateFormat.format(new Date());
        }

        Room simpleRoom = new Room(name);
        ROOM_MAP.put(simpleRoom.getRoomId(), simpleRoom);
        return simpleRoom;
    }

    /**
     * 채팅방 제거
     *
     * @param roomId
     */
    public Room removeRoomReturn(String roomId)
    {
        return ROOM_MAP.remove(roomId);
    }

    /**
     * 채팅방 참여자수
     *
     * @param roomId
     * @return
     */
    public int getSessionCntByRoomId(String roomId)
    {
        if (!ROOM_MAP.containsKey(roomId))
        {
            return 0;
        }

        Map<String, SimpMessageHeaderAccessor> sessions = ROOM_MAP.get(roomId).getSessionMap();
        return sessions.isEmpty() ? 0 : sessions.size();
    }

    /**
     * 채팅방에 세션 추가
     *
     * @param roomId
     * @param session
     */
    public void addSessionByRoomId(String roomId, SimpMessageHeaderAccessor session)
    {
        if (!ROOM_MAP.containsKey(roomId))
        {
            throw new RuntimeException("No exist room. " + roomId);
        }

        ROOM_MAP.get(roomId).putSession(session);
    }

    public void removeSessionByRoomId(String roomId, SimpMessageHeaderAccessor session)
    {
        if (!ROOM_MAP.containsKey(roomId))
        {
            return;
        }

        ROOM_MAP.get(roomId).removeSession(session);
    }
}
