package com.wonhyong.talk.board.controller;

import com.wonhyong.talk.board.dto.CommentDto;
import com.wonhyong.talk.board.service.CommentService;
import com.wonhyong.talk.member.domain.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/boards/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public Iterable<CommentDto.Response> getAllCommentsInPost(@PathVariable("postId") Long postId) {
        return commentService.findAllCommentsInPost(postId);
    }

    @PostMapping
    public CommentDto.Response createComment(@PathVariable("postId") Long postId,
                                    @AuthenticationPrincipal MemberDetails member,
                                    @RequestBody CommentDto.Request commentRequestDto) {
        return commentService.create(postId, member, commentRequestDto);
    }

    @PutMapping("/{commentId}")
    public CommentDto.Response updateComment(@PathVariable("commentId") Long commentId,
                                    @AuthenticationPrincipal MemberDetails member,
                                    @RequestBody CommentDto.Request commentRequestDto) throws IllegalAccessException {

        return commentService.update(commentId, member, commentRequestDto);
    }

    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long id) {
        commentService.delete(id);
        return "Comment(" + id + ") deleted";
    }
}
