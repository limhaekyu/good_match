package com.example.good_match.domain.chat.service;

import com.example.good_match.domain.chat.model.ChatRoom;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

public interface MessageService {

    List<ChatRoom> findAllRoom();

    ChatRoom findById(String roomId);
    ChatRoom createRoom(String name);
    <T> void sendMessage(WebSocketSession session, T message);
}
