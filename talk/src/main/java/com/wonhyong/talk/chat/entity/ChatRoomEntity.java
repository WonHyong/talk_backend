package com.wonhyong.talk.chat.entity;

import com.wonhyong.talk.chat.dto.ChatRoom;
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
public class ChatRoomEntity {

    @Id
    @Column(name = "room_id")
    private String roomId;

    @Column(length = 50, nullable = false)
    private String name;

    public ChatRoom toModel() {
        return ChatRoom.builder()
                .roomId(roomId)
                .name(name)
                .build();
    }

    public static ChatRoomEntity from(ChatRoom chatRoom) {
        return new ChatRoomEntity(chatRoom.getRoomId(), chatRoom.getName());
    }
}
