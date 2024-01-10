package com.wonhyong.talk.board.controller;

import com.wonhyong.talk.board.dto.CommentRequestDto;
import com.wonhyong.talk.board.dto.CommentResponseDto;
import com.wonhyong.talk.board.dto.PostRequestDto;
import com.wonhyong.talk.board.dto.PostResponseDto;
import com.wonhyong.talk.board.service.CommentService;
import com.wonhyong.talk.board.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/boards")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{postId}/comments")
    public Iterable<CommentResponseDto> getAllCommentsInPost(@PathVariable("postId") Long postId) {
        return commentService.findAllCommentsInPost(postId);
    }

    @PostMapping("/{postId}/comments")
    public CommentResponseDto createComment(@PathVariable("postId") Long postId,
                                            @RequestBody CommentRequestDto commentRequestDto) {
        log.info("board: In" + postId + " Comment created: " + commentRequestDto);
        return commentService.create(postId, commentRequestDto);
    }

    @PutMapping("/{postId}/{commentId}")
    public CommentResponseDto updateComment(@PathVariable("postId") Long postId,
                                         @PathVariable("commentId") Long commentId,
                                         @RequestBody CommentRequestDto commentRequestDto) {
        log.info("board: comment updated: " + commentRequestDto);
        return commentService.update(commentId, commentRequestDto);
    }

    @DeleteMapping("/{postId}/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long id) {
        log.info("board: Post deleted: " + id);
        commentService.delete(id);
    }
}
