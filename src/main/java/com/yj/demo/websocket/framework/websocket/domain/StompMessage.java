package com.yj.demo.websocket.framework.websocket.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StompMessage extends SimpleMessage
{
    private String roomId;
    private String writerName;
}
