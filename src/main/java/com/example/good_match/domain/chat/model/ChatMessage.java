package com.example.good_match.domain.chat.model;

import lombok.*;

@Getter
@Setter
public class ChatMessage {

    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
}
