package com.example.good_match.domain.chat.repository;

import com.example.good_match.domain.chat.model.ChatRoom;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {

    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        List<ChatRoom> msgRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(msgRooms); // 채팅방 최근순서로 반환
        return msgRooms;
    }

    public ChatRoom findByRoomId(String roomId) {
        return chatRoomMap.get(roomId);
    }

    public ChatRoom createChatRoom(String name) {
        ChatRoom room = ChatRoom.create(name);
        chatRoomMap.put(room.getRoomId(), room);
        return room;
    }

}
