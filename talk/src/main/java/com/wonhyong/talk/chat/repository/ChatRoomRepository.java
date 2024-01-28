package com.wonhyong.talk.chat.repository;

import com.wonhyong.talk.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
}
