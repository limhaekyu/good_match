package com.example.good_match.domain.chat.controller;

import com.example.good_match.domain.chat.model.ChatRoom;
import com.example.good_match.domain.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final MessageService msgService;

    @PostMapping("")
    public ChatRoom creatRoom(@RequestParam String name) {
        return msgService.createRoom(name);
    }

    @GetMapping("")
    public List<ChatRoom> findAllRoom() {
        return msgService.findAllRoom();
    }
}
