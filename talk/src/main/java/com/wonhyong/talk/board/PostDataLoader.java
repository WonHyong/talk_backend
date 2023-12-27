package com.wonhyong.talk.board;

import com.wonhyong.talk.board.entity.Post;
import com.wonhyong.talk.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostDataLoader {
    private final PostRepository postRepository;

    @PostConstruct
    private void loadBoardData() {
        List<Post> sampleBoards = new ArrayList<>(100);

        for (int i = 0; i < 100; i++) {
            Post post = Post.builder()
                    .title("Title " + i)
                    .content("Content " + i)
                    .build();
            sampleBoards.add(post);
        }

        postRepository.saveAll(sampleBoards);
    }
}
