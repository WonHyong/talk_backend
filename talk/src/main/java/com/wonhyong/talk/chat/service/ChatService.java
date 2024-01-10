package com.wonhyong.talk.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonhyong.talk.chat.dto.ChatMessage;
import com.wonhyong.talk.chat.dto.ChatRoom;
import com.wonhyong.talk.chat.entity.ChatMessageEntity;
import com.wonhyong.talk.chat.entity.ChatRoomEntity;
import com.wonhyong.talk.chat.repository.ChatMessageRepository;
import com.wonhyong.talk.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRooms;

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @PostConstruct
    private void init() {
        chatRooms = chatRoomRepository.findAll().stream()
                .map(ChatRoomEntity::toModel)
                .collect(Collectors.toMap(
                        ChatRoom::getRoomId,
                        room -> room,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new));
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
        chatRoomRepository.save(ChatRoomEntity.from(chatRoom));
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
        chatMessageRepository.save(ChatMessageEntity.from(chatMessage));
    }

    private void enterRoom(WebSocketSession session, ChatRoom chatRoom, ChatMessage chatMessage) {
        chatRoom.addSession(session);
        chatMessage.setMessage(chatMessage.getSender() + " entered");
    }

    private void sendMessageToRoom(ChatRoom room, ChatMessage msg) {
        room.getSessions().parallelStream().forEach(session -> sendMessage(session, msg));
    }

    public void sendErrorMessage(WebSocketSession session, String message) {
        sendMessage(session, message);
    }

    private <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }


}
