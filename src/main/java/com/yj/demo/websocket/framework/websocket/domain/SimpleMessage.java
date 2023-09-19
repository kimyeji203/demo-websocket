package com.yj.demo.websocket.framework.websocket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yj.demo.websocket.framework.websocket.WebSocketConst.MESSAGE_TYPES;
import lombok.Data;

@Data
public class SimpleMessage
{
    private Boolean isMine;
    private MESSAGE_TYPES type;
    private String context;

    @JsonIgnore
    private String writer;
}
