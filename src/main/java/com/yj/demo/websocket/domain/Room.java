package com.yj.demo.websocket.domain;

import lombok.Data;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 채탕방 도메인
 */
@Data
public class Room
{
    private String roomId;
    private String roomName;
    private Integer max;
    private Map<String, SimpMessageHeaderAccessor> sessionMap;

    public Room(String name)
    {
        this.roomId = UUID.randomUUID().toString();
        this.roomName = name;
        this.max = 5;
        this.sessionMap = new LinkedHashMap<>();
    }

    public void putSession(SimpMessageHeaderAccessor session)
    {
        this.sessionMap.put(session.getSessionId(), session);
    }

    public void removeSession(SimpMessageHeaderAccessor session)
    {
        this.sessionMap.remove(session.getSessionId());
    }
}
