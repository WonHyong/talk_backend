package com.wonhyong.talk.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
public class ChatRoom {

    private final String roomId;
    private final String name;
    private final Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

    @RequiredArgsConstructor
    @Getter
    public static class Response {
        private final String roomId;
        private final String name;

        public static Response from(ChatRoom chatRoom) {
            return new Response(chatRoom.getRoomId(), chatRoom.getName());
        }
    }
}
