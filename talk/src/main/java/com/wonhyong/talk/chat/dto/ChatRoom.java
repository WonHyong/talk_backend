package com.wonhyong.talk.chat.dto;

import lombok.Builder;
import lombok.Getter;
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
}
