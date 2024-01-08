package com.wonhyong.talk.chat.controller;

import com.wonhyong.talk.chat.dto.ChatRoom;
import com.wonhyong.talk.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    @GetMapping
    public Iterable<ChatRoom> findAllRoom() {
        return chatService.findAllRooms();
    }
}
