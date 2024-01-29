package com.wonhyong.talk.chat.repository;

import com.wonhyong.talk.chat.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
}
