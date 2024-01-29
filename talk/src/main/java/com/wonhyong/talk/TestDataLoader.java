package com.wonhyong.talk;

import com.wonhyong.talk.board.model.Comment;
import com.wonhyong.talk.board.model.Post;
import com.wonhyong.talk.board.repository.CommentRepository;
import com.wonhyong.talk.board.repository.PostRepository;
import com.wonhyong.talk.chat.model.ChatRoom;
import com.wonhyong.talk.chat.repository.ChatRoomRepository;
import com.wonhyong.talk.member.domain.Member;
import com.wonhyong.talk.member.domain.Role;
import com.wonhyong.talk.member.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TestDataLoader {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void loadTestData() {
        clearAllData();

        Member admin = makeMember("ADMIN", "1234", Role.ADMIN);
        Member user1 = makeMember("USER1", "1234", Role.USER);
        Member user2 = makeMember("USER2", "1234", Role.USER);

        memberRepository.saveAll(List.of(admin, user1, user2));

        loadBoardData(10, admin);
        loadBoardData(20, user1);
        loadBoardData(30, user2);

        loadCommentData(5, user2);

        //loadChatRoomData(10);
    }

    private void clearAllData() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
        chatRoomRepository.deleteAll();
    }

    private Member makeMember(String name, String password, Role role) {
        return Member.builder()
                .name(name)
                .email(name + "@lo.com")
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
    }

    private void loadBoardData(int size, Member writer) {
        List<Post> sampleBoards = new ArrayList<>(size);

        for (int i = 1; i <= size; i++) {
            Post post = Post.builder()
                    .title("Title " + i + ":" + writer.getName())
                    .content("Content " + i)
                    .writer(writer)
                    .build();

            sampleBoards.add(post);
        }

        postRepository.saveAll(sampleBoards);
    }

    private void loadCommentData(int size, Member writer) {
        Iterable<Post> targetPosts = postRepository.findAll();

        List<Comment> sampleComments = new ArrayList<>(size);

        for (Post target : targetPosts) {
            for (int i=1; i<=size; i++) {
                Comment comment = Comment.builder()
                        .content("comment " + i + "in post: " + target.getTitle())
                        .post(target)
                        .writer(writer)
                        .build();

                sampleComments.add(comment);
            }
        }

        commentRepository.saveAll(sampleComments);
    }

    private void loadChatRoomData(int size) {
        List<com.wonhyong.talk.chat.dto.ChatRoom> sampleRooms = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            com.wonhyong.talk.chat.dto.ChatRoom room = com.wonhyong.talk.chat.dto.ChatRoom.builder()
                    .roomId(UUID.randomUUID().toString())
                    .name("test_room" + i)
                    .build();
            sampleRooms.add(room);
        }

        chatRoomRepository.saveAll(sampleRooms.stream()
                .map(ChatRoom::from)
                .collect(Collectors.toList()));
    }
}
