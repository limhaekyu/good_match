package com.example.good_match.domain.chat.repository;

import com.example.good_match.domain.chat.model.ChatRoom;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class MessageRoomRepository {

    private Map<String, ChatRoom> msgRoomMap;

    @PostConstruct
    private void init() {
        msgRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        List<ChatRoom> msgRooms = new ArrayList<>(msgRoomMap.values());
        Collections.reverse(msgRooms);
        return msgRooms;
    }

    public ChatRoom findByRoomId(String roomId) {
        return msgRoomMap.get(roomId);
    }

    public ChatRoom createMsgRoom(String name) {
        ChatRoom room = ChatRoom.builder().roomId(name).build();
        msgRoomMap.put(room.getRoomId(), room);
        return room;
    }

}
