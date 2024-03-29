package com.wonhyong.talk.chat.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "room_id")
    private String room;

    @Column
    private String type;

    @Column
    private String sender;

    @Column
    private String message;

    @Builder
    private ChatMessage(String type, String sender, String message, String room) {
        this.type = type;
        this.sender = sender;
        this.message = message;
        this.room = room;
    }

    public static ChatMessage from(com.wonhyong.talk.chat.dto.ChatMessage chatMessage) {
        return ChatMessage.builder()
                .type(chatMessage.getType().name())
                .sender(chatMessage.getSender())
                .message(chatMessage.getMessage())
                .room(chatMessage.getRoomId())
                .build();
    }
}
