package com.wonhyong.talk.chat.repository;

import com.wonhyong.talk.chat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> { }
