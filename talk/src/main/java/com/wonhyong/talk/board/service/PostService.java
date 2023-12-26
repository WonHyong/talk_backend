package com.wonhyong.talk.board.service;

import com.wonhyong.talk.board.dto.PostRequestDto;
import com.wonhyong.talk.board.dto.PostResponseDto;
import com.wonhyong.talk.board.entity.Post;
import com.wonhyong.talk.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public Slice<PostResponseDto> pages(Pageable pageable) {
        return postRepository.findSliceBy(pageable)
                .map(PostResponseDto::from);
    }

    @Transactional(readOnly = true)
    public Optional<PostResponseDto> findById(Long id) {
        return postRepository.findById(id)
                .map(PostResponseDto::from);
    }

    @Transactional
    public PostResponseDto create(PostRequestDto postRequestDto) {
        Post post = postRequestDto.toEntity();
        return PostResponseDto.from(postRepository.save(post));
    }

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        post.update(postRequestDto.getTitle(), postRequestDto.getContent());
        postRepository.save(post);
        return PostResponseDto.from(post);
    }

    @Transactional
    public void delete(Long id) {
        postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        postRepository.deleteById(id);
    }
}
