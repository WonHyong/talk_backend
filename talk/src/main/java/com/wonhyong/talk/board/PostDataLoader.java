package com.wonhyong.talk.board;

import com.wonhyong.talk.board.model.Post;
import com.wonhyong.talk.board.repository.PostRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostDataLoader {
    private final PostRepository postRepository;

    public PostDataLoader(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostConstruct
    private void loadBoardData() {
        postRepository.deleteAll();

        List<Post> sampleBoards = new ArrayList<>(30);

        for (int i = 0; i < 30; i++) {
            Post post = Post.builder()
                    .title("Title " + i)
                    .content("Content " + i)
                    .member(null)
                    .view(0)
                    .build();

            sampleBoards.add(post);
        }

        postRepository.saveAll(sampleBoards);
    }
}
