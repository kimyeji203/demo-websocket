package com.yj.demo.websocket.domain;

import lombok.Data;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class Room
{
    private String roomId;
    private String roomName;
    private Integer max;
    private Map<String, SimpMessageHeaderAccessor> sessionMap;

    public Room (String name)
    {
        this.roomId = UUID.randomUUID().toString();
        this.roomName = name;
        this.max = 5;
        this.sessionMap = new LinkedHashMap<>();
    }
}
