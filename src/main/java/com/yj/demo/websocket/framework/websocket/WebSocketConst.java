package com.yj.demo.websocket.framework.websocket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class WebSocketConst
{
    /**
     * 웹소켓 메시지 유형
     */
    @JsonFormat(shape = Shape.STRING)
    public enum MESSAGE_TYPES
    {
        OPEN, CLOSE, TEXT;
    }

    /**
     * session attribute key names
     */
    // 세션 유저명
    public static final String SESSION_ATTR_NAME = "name";
}
