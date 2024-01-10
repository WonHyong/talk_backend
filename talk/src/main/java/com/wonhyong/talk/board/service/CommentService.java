package com.wonhyong.talk.board.service;

import com.wonhyong.talk.board.dto.CommentRequestDto;
import com.wonhyong.talk.board.dto.CommentResponseDto;
import com.wonhyong.talk.board.dto.PostRequestDto;
import com.wonhyong.talk.board.dto.PostResponseDto;
import com.wonhyong.talk.board.entity.Comment;
import com.wonhyong.talk.board.entity.Post;
import com.wonhyong.talk.board.repository.CommentRepository;
import com.wonhyong.talk.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public Iterable<CommentResponseDto> findAllCommentsInPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("NO POST FOR " + postId));

        List<Comment> comments = post.getComments();

        return comments.stream()
                .map(CommentResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto create(Long postId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRequestDto.toEntity();
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("NO POST FOR: " + postId));
        comment.setPost(post);
        commentRepository.save(comment);
        return CommentResponseDto.from(comment);
    }

    @Transactional
    public CommentResponseDto update(Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("No Comment For: " + commentId));

        comment.update(commentRequestDto.getContent());
        commentRepository.save(comment);
        return CommentResponseDto.from(comment);
    }

    @Transactional
    public void delete(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new IllegalArgumentException("No Comment For: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }
}
