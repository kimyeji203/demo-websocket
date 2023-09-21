package com.yj.demo.websocket.domain;

import com.yj.demo.websocket.framework.websocket.WebSocketConst.MESSAGE_TYPES;
import lombok.Data;

/**
 * 웹소켓 메시지
 */
@Data
public class SocketMessage
{
    private MESSAGE_TYPES type; // 메시지 유형
    private String context; // 메시지 내용
    private String writerKey; // 작성자 식별키
    private Boolean isMine; // 본인 메시지 여부
}
