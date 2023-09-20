package com.yj.demo.websocket.framework.websocket.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
public class StompRoom
{
    private String roomId;
    private String roomName;
    private Map<String, WebSocketSession> sessionMap;

    public StompRoom(String name)
    {
        this.roomId = UUID.randomUUID().toString();
        this.sessionMap = new HashMap<>();
        this.roomName = name;
    }
}
