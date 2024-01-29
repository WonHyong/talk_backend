package com.wonhyong.talk.chat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rooms")
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {

    @Id
    @Column(name = "room_id")
    private String roomId;

    @Column(length = 50, nullable = false)
    private String name;

    public com.wonhyong.talk.chat.dto.ChatRoom toModel() {
        return com.wonhyong.talk.chat.dto.ChatRoom.builder()
                .roomId(roomId)
                .name(name)
                .build();
    }

    public static ChatRoom from(com.wonhyong.talk.chat.dto.ChatRoom chatRoom) {
        return new ChatRoom(chatRoom.getRoomId(), chatRoom.getName());
    }
}
