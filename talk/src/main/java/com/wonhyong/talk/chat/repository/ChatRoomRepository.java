package com.wonhyong.talk.chat.repository;

import com.wonhyong.talk.chat.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, String> {
}
