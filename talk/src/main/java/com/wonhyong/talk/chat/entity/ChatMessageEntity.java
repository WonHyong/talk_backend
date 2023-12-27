package com.wonhyong.talk.chat.entity;

import com.wonhyong.talk.chat.dto.ChatMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "messages")
@NoArgsConstructor
@Getter
public class ChatMessageEntity {

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
    private ChatMessageEntity(String type, String sender, String message, String room) {
        this.type = type;
        this.sender = sender;
        this.message = message;
        this.room = room;
    }

    public static ChatMessageEntity from(ChatMessage chatMessage) {
        return ChatMessageEntity.builder()
                .type(chatMessage.getType().name())
                .sender(chatMessage.getSender())
                .message(chatMessage.getMessage())
                .room(chatMessage.getRoomId())
                .build();
    }
}
