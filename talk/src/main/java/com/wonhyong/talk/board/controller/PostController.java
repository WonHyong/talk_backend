package com.wonhyong.talk.board.controller;

import com.wonhyong.talk.board.dto.PostDto;
import com.wonhyong.talk.board.service.PostService;
import com.wonhyong.talk.member.domain.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public Iterable<PostDto.ListResponse> getPosts(Pageable pageable,
                                                   @AuthenticationPrincipal MemberDetails member) {
        return postService.getPage(member, pageable);
    }

    @GetMapping("/{id}")
    public PostDto.DetailResponse getPostById(@PathVariable("id") Long id,
                                              @AuthenticationPrincipal MemberDetails member) {
        return postService.findById(member, id);
    }

    @PostMapping
    public PostDto.DetailResponse createPost(@Valid @RequestBody PostDto.Request postRequestDto,
                                             @AuthenticationPrincipal MemberDetails member) {
        return postService.create(member, postRequestDto);
    }

    @PutMapping("/{id}")
    public PostDto.DetailResponse updatePost(@PathVariable("id") Long id,
                              @Valid @RequestBody PostDto.Request postRequestDto,
                              @AuthenticationPrincipal MemberDetails member) throws Exception {

        return postService.update(id, member, postRequestDto);
    }

    @PostMapping("/{id}/like")
    public boolean increaseLike(@PathVariable("id") Long id,
                               @AuthenticationPrincipal MemberDetails member) {
        return postService.increaseLike(id, member);
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable("id") Long id,
                             @AuthenticationPrincipal MemberDetails member) throws Exception {
        postService.delete(id, member);
        return "Post(" + id + ") deleted";
    }
}