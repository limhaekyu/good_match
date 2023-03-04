package com.example.good_match.global.handler;

import com.example.good_match.domain.chat.model.ChatRoom;
import com.example.good_match.domain.chat.model.Message;
import com.example.good_match.domain.chat.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final MessageService msgService;
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : {}", payload);

        Message msg = objectMapper.readValue(payload, Message.class);
        ChatRoom room = msgService.findById(msg.getRoomId());
        log.info("MessageType : " + msg.getMessageType());
        room.handleActions(session, msg, msgService);
    }
}
