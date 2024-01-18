package com.wonhyong.talk.board.controller;

import com.wonhyong.talk.board.dto.PostDto;
import com.wonhyong.talk.board.service.PostService;
import com.wonhyong.talk.member.domain.MemberDetails;
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
    public Iterable<PostDto> getPosts(Pageable pageable, @AuthenticationPrincipal MemberDetails member) throws Exception {
        return postService.getPage(pageable, member);
    }

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable("id")Long id, @AuthenticationPrincipal MemberDetails member) throws Exception {
        return postService.findById(member, id);
    }

    @PostMapping
    public PostDto createPost(@RequestBody PostDto postRequestDto,
                                      @AuthenticationPrincipal MemberDetails member) throws Exception {
        return postService.create(member, postRequestDto);
    }

    @PutMapping("/{id}")
    public PostDto updatePost(@PathVariable("id") Long id,
                              @RequestBody PostDto postRequestDto,
                              @AuthenticationPrincipal MemberDetails member) throws Exception {

        return postService.update(id, member, postRequestDto);
    }

    @PostMapping("/{id}/like")
    public String increaseLike(@PathVariable("id") Long id,
                               @AuthenticationPrincipal MemberDetails member) throws Exception {
        boolean success = postService.increaseLike(id, member);
        return "Like to Post(" + id + ") is " + success;
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable("id") Long id, @AuthenticationPrincipal MemberDetails member) throws Exception {
        postService.delete(id, member);
        return "Post(" + id + ") deleted";
    }
}