package com.wonhyong.talk.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonhyong.talk.chat.dto.ChatMessage;
import com.wonhyong.talk.chat.service.ChatService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) {
        String payload = message.getPayload();
        log.info("payload {}", payload);
        try {
            ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
            chatService.handleMessageActions(session, chatMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
