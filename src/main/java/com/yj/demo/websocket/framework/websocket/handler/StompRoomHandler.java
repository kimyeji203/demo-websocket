package com.yj.demo.websocket.framework.websocket.handler;

import com.yj.demo.websocket.framework.websocket.domain.StompRoom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
public class StompRoomHandler
{
    private final Map<String, StompRoom> ROOM_MAP = new LinkedHashMap<>();
    private final Map<String, Map<String, SimpMessageHeaderAccessor>> ROOM_SESSION_MAP = new LinkedHashMap<>();

    /**
     * 채팅방 전체 목록 조회
     *
     * @return
     */
    public List<StompRoom> findAllRooms()
    {
        return ROOM_MAP.values().stream().toList();
    }

    /**
     * ID로 채팅방 조회
     *
     * @param id
     * @return
     */
    public StompRoom findRoomById(String id)
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
    public StompRoom createRoomReturn(String name)
    {
        if (StringUtils.isBlank(name))
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm-ss");
            name = simpleDateFormat.format(new Date());
        }

        StompRoom simpleRoom = new StompRoom(name);
        ROOM_MAP.put(simpleRoom.getRoomId(), simpleRoom);
        return simpleRoom;
    }

    /**
     * 채팅방 제거
     *
     * @param roomId
     */
    public StompRoom removeRoomReturn(String roomId)
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
        if (!ROOM_SESSION_MAP.containsKey(roomId))
        {
            return 0;
        }

        Map<String, SimpMessageHeaderAccessor> sessions = ROOM_SESSION_MAP.get(roomId);
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
        Map<String, SimpMessageHeaderAccessor> sessions = ROOM_SESSION_MAP.get(roomId);
        if (sessions == null || sessions.isEmpty())
        {
            sessions = new LinkedHashMap<>();
        }
        sessions.put(session.getSessionId(), session);
    }

    public void removeSessionByRoomId(String roomId, SimpMessageHeaderAccessor session)
    {
        if (!ROOM_SESSION_MAP.containsKey(roomId))
        {
            return;
        }

        ROOM_SESSION_MAP.get(roomId).remove(session.getSessionId());
    }
}
