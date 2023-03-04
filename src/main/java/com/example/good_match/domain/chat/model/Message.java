package com.example.good_match.domain.chat.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {
    private MessageType messageType;
    private String roomId;
    private String sender;
    private String message;
}
