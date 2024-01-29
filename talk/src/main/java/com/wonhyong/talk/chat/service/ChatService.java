package com.wonhyong.talk.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonhyong.talk.chat.model.ChatMessage;
import com.wonhyong.talk.chat.model.ChatRoom;
import com.wonhyong.talk.chat.repository.ChatMessageRepository;
import com.wonhyong.talk.chat.repository.ChatRoomRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private Map<String, com.wonhyong.talk.chat.dto.ChatRoom> chatRooms;

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @PostConstruct
    private void init() {
        chatRooms = chatRoomRepository.findAll().stream()
                .map(ChatRoom::toModel)
                .collect(Collectors.toMap(
                        com.wonhyong.talk.chat.dto.ChatRoom::getRoomId,
                        room -> room,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new));
    }

    public List<com.wonhyong.talk.chat.dto.ChatRoom.Response> findAllRooms() {
        return chatRooms.values().stream()
                .map(com.wonhyong.talk.chat.dto.ChatRoom.Response::from)
                .collect(Collectors.toList());
    }

    public Optional<com.wonhyong.talk.chat.dto.ChatRoom> findRoomById(String roomId) {
        return Optional.ofNullable(chatRooms.get(roomId));
    }

    public com.wonhyong.talk.chat.dto.ChatRoom createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        com.wonhyong.talk.chat.dto.ChatRoom chatRoom = com.wonhyong.talk.chat.dto.ChatRoom.builder()
                .roomId(randomId)
                .name(name)
                .build();
        chatRooms.put(randomId, chatRoom);
        chatRoomRepository.save(ChatRoom.from(chatRoom));
        return chatRoom;
    }

    public void handleMessageActions(WebSocketSession session, com.wonhyong.talk.chat.dto.ChatMessage chatMessage) {
        String roomId = chatMessage.getRoomId();
        com.wonhyong.talk.chat.dto.ChatRoom room = findRoomById(roomId).orElseThrow(() ->
                new IllegalArgumentException("NO ROOM ID FOR " + roomId));

        if (chatMessage.getType().equals(com.wonhyong.talk.chat.dto.ChatMessage.MessageType.ENTER)) {
            enterRoom(session, room, chatMessage);
        }

        sendMessageToRoom(room, chatMessage);
        chatMessageRepository.save(ChatMessage.from(chatMessage));
    }

    private void enterRoom(WebSocketSession session, com.wonhyong.talk.chat.dto.ChatRoom chatRoom, com.wonhyong.talk.chat.dto.ChatMessage chatMessage) {
        chatRoom.addSession(session);
        chatMessage.setMessage(chatMessage.getSender() + " entered");
    }

    private void sendMessageToRoom(com.wonhyong.talk.chat.dto.ChatRoom room, com.wonhyong.talk.chat.dto.ChatMessage msg) {
        List<WebSocketSession> toRemoveSessions = new ArrayList<>();
        room.getSessions().parallelStream().forEach(session -> {
            try {
                sendMessage(session, msg);
            } catch (Exception e) {
                log.error(e.getMessage());
                toRemoveSessions.add(session);
            }
        });
        toRemoveSessions.forEach(room::removeSession);
    }

    private <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
