package com.yj.demo.websocket.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoomSocketMessage extends SocketMessage
{
    private String roomId;
    private String writerName;
}
