package com.wonhyong.talk.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonhyong.talk.chat.dto.ChatMessage;
import com.wonhyong.talk.chat.dto.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRooms() {
        return new ArrayList<>(chatRooms.values());
    }

    public Optional<ChatRoom> findRoomById(String roomId) {
        return Optional.ofNullable(chatRooms.get(roomId));
    }

    public ChatRoom createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomId)
                .name(name)
                .build();
        chatRooms.put(randomId, chatRoom);
        return chatRoom;
    }

    public void handleMessageActions(WebSocketSession session, ChatMessage chatMessage) {
        String roomId = chatMessage.getRoomId();
        ChatRoom room = findRoomById(roomId).orElseThrow(() ->
                new IllegalArgumentException("NO ROOM ID FOR " + roomId));

        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            enterRoom(session, room, chatMessage);
        }

        sendMessageToRoom(room, chatMessage);
    }

    private void enterRoom(WebSocketSession session, ChatRoom chatRoom, ChatMessage chatMessage) {
        chatRoom.addSession(session);
        chatMessage.setMessage(chatMessage.getSender() + " entered");
    }

    private void sendMessageToRoom(ChatRoom room, ChatMessage msg) {
        room.getSessions().parallelStream().forEach(session -> sendMessage(session, msg));
    }

    private <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
