package com.wonhyong.talk.board.controller;

import com.wonhyong.talk.board.dto.PostRequestDto;
import com.wonhyong.talk.board.dto.PostResponseDto;
import com.wonhyong.talk.board.service.PostService;
import com.wonhyong.talk.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/boards")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public Iterable<PostResponseDto> getPosts(Pageable pageable) {
        return postService.pages(pageable);
    }

    @GetMapping("/{id}")
    public Optional<PostResponseDto> getPostById(@PathVariable("id") Long id) {
        return postService.findById(id);
    }

    @PostMapping
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto) {
        log.info("board: Post created: " + postRequestDto);
        return postService.create(postRequestDto);
    }

    @PutMapping("/{id}")
    public PostResponseDto updatePost(@PathVariable("id") Long id, @RequestBody PostRequestDto postRequestDto) {
        log.info("board: Post updated: " + postRequestDto);
        return postService.update(id, postRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Long id) {
        log.info("board: Post deleted: " + id);
        postService.delete(id);
    }
}