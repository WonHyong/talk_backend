package com.wonhyong.talk.board.controller;

import com.wonhyong.talk.board.dto.PostRequestDto;
import com.wonhyong.talk.board.dto.PostResponseDto;
import com.wonhyong.talk.board.service.PostService;
import com.wonhyong.talk.member.domain.MemberDetails;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.wonhyong.talk.member.domain.Member;

import java.util.Optional;

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
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto,
                                      @AuthenticationPrincipal MemberDetails member) {
        System.out.println(member);
        return postService.create(postRequestDto, member);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable("id") Long id, @RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.ok(postService.update(id, postRequestDto));
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Long id) {
        postService.delete(id);
    }
}
