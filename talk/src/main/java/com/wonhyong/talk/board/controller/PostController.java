package com.wonhyong.talk.board.controller;

import com.wonhyong.talk.board.dto.PostDto;
import com.wonhyong.talk.board.service.PostService;
import com.wonhyong.talk.member.domain.MemberDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public Iterable<PostDto.ListResponse> getPosts(Pageable pageable) {
        return postService.getPage(pageable);
    }

    @GetMapping("/{id}")
    public PostDto.DetailResponse getPostById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @PostMapping
    public PostDto.DetailResponse createPost(@Valid @RequestBody PostDto.Request postRequestDto,
                                             @AuthenticationPrincipal MemberDetails member) {
        return postService.create(member, postRequestDto);
    }

    @PutMapping("/{id}")
    public PostDto.DetailResponse updatePost(@PathVariable Long id,
                                                             @Valid @RequestBody PostDto.Request postRequestDto,
                                                             @AuthenticationPrincipal MemberDetails member) {
        return postService.update(id, member, postRequestDto);
    }

    @PostMapping("/{id}/like")
    public boolean increaseLike(@PathVariable Long id,
                                @AuthenticationPrincipal MemberDetails member) {
        return postService.increaseLike(id, member);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id,
                           @AuthenticationPrincipal MemberDetails member) {
        postService.delete(id, member);
    }
}