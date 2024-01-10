package com.wonhyong.talk.chat.repository;

import com.wonhyong.talk.chat.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> { }
