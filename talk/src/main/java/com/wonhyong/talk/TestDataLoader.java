package com.wonhyong.talk;

import com.wonhyong.talk.board.model.Post;
import com.wonhyong.talk.board.repository.PostRepository;
import com.wonhyong.talk.chat.dto.ChatRoom;
import com.wonhyong.talk.chat.entity.ChatRoomEntity;
import com.wonhyong.talk.chat.repository.ChatRoomRepository;
import com.wonhyong.talk.member.domain.Member;
import com.wonhyong.talk.member.domain.Role;
import com.wonhyong.talk.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TestDataLoader {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void loadTestData() {
        clearAllData();

        String adminName = "ADMIN";
        String adminPw = "1234";
        loadAdminUserData(adminName, adminPw);

        Member samplePostWriter = memberRepository.findByName(adminName).get();
        loadBoardData(30, samplePostWriter);

        //loadChatRoomData(10);
    }

    private void clearAllData() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
        chatRoomRepository.deleteAll();
    }

    private void loadAdminUserData(String name, String password) {
        Member admin = Member.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .role(Role.ADMIN)
                .build();

        memberRepository.save(admin);
    }

    private void loadBoardData(int size, Member writer) {
        List<Post> sampleBoards = new ArrayList<>(size);

        for (int i = 1; i <= size; i++) {
            Post post = Post.builder()
                    .title("Title " + i)
                    .content("Content " + i)
                    .member(writer)
                    .build();

            sampleBoards.add(post);
        }

        postRepository.saveAll(sampleBoards);
    }

    private void loadChatRoomData(int size) {
        List<ChatRoom> sampleRooms = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
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
