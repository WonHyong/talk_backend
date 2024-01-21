package com.wonhyong.talk.board.controller;

import com.wonhyong.talk.board.dto.CommentDto;
import com.wonhyong.talk.board.service.CommentService;
import com.wonhyong.talk.member.domain.MemberDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/boards/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public Iterable<CommentDto.Response> getAllCommentsInPost(@PathVariable Long postId) {
        return commentService.findAllCommentsInPost(postId);
    }

    @PostMapping
    public CommentDto.Response createComment(@PathVariable Long postId,
                                             @AuthenticationPrincipal MemberDetails member,
                                             @Valid @RequestBody CommentDto.Request commentRequestDto) {
        return commentService.create(postId, member, commentRequestDto);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto.Response> updateComment(@PathVariable Long postId,
                                                             @PathVariable Long commentId,
                                                             @AuthenticationPrincipal MemberDetails member,
                                                             @Valid @RequestBody CommentDto.Request commentRequestDto) throws IllegalAccessException {

        return (commentService.isExistById(commentId)) ? ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(postId, member, commentRequestDto)) : ResponseEntity.status(HttpStatus.OK).body(commentService.update(commentId, member, commentRequestDto));
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long id,
                              @AuthenticationPrincipal MemberDetails member) throws IllegalAccessException {
        commentService.delete(id, member);
    }
}
