package com.yj.demo.websocket.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 채팅룸 소켓 메시지 도메인
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoomSocketMessage extends SocketMessage
{
    private String roomId; // 채팅룸 아이디
    private String writerName; // 작성자 이름
}
