package com.yj.demo.websocket.framework.websocket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class WebSocketConst
{
    @JsonFormat(shape = Shape.STRING)
    public enum MESSAGE_TYPES
    {
        OPEN, CLOSE, TEXT;
    }
}
