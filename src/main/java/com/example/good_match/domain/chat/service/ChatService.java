package com.example.good_match.domain.chat.service;

import com.example.good_match.domain.chat.model.ChatRoom;

import java.util.List;

public interface ChatService {

    List<ChatRoom> findAllRoom();
    ChatRoom findRoomById(String roomId);
}
