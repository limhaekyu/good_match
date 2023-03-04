package com.example.good_match.domain.chat.model;

import com.example.good_match.domain.chat.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Log4j2
public class ChatRoom {
    private String roomId;
    private static Set<WebSocketSession> sessions = new HashSet<>();

    public void handleActions(WebSocketSession session, Message message, MessageService messageService) {
        if (message.getMessageType().equals(MessageType.ENTER)) {
            sessions.add(session);
            message.setMessage(message.getSender() + "님이 입장했습니다.");
        }
        sendMessage(message, messageService);
    }

    public <T> void sendMessage(T message, MessageService messageService) {
        sessions.parallelStream().forEach(session -> messageService.sendMessage(session, message));
    }
}
