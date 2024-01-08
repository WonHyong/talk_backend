package com.wonhyong.talk.chat;


import com.wonhyong.talk.chat.dto.ChatRoom;
import com.wonhyong.talk.chat.entity.ChatRoomEntity;
import com.wonhyong.talk.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Construct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatDataLoader {

    private final ChatRoomRepository chatRoomRepository;

    @PostConstruct
    private void loadChatRoomData() {
        List<ChatRoom> sampleRooms = new ArrayList<>(100);

        for (int i = 0; i < 10; i++) {
            ChatRoom room = ChatRoom.builder()
                    .roomId(UUID.randomUUID().toString())
                    .name("test_room" + i)
                    .build();
            sampleRooms.add(room);
        }

        chatRoomRepository.saveAll(sampleRooms.stream()
                .map(ChatRoomEntity::from)
                .collect(Collectors.toList()));
    }
}
