package com.wonhyong.talk.board.service;

import com.wonhyong.talk.board.dto.CommentDto;
import com.wonhyong.talk.board.model.Comment;
import com.wonhyong.talk.board.model.Post;
import com.wonhyong.talk.board.repository.CommentRepository;
import com.wonhyong.talk.member.domain.Member;
import com.wonhyong.talk.member.domain.MemberDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;

    @Transactional(readOnly = true)
    public Iterable<CommentDto.Response> findAllCommentsInPost(@NonNull Long postId) {
        Post post = postService.findPostById(postId);

        return post.getComments().stream()
                .map(CommentDto.Response::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto.Response create(Long postId, MemberDetails member, CommentDto.Request commentDto) {
        Post post = postService.findPostById(postId);
        Member currentUser = postService.findUser(member);

        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .post(post)
                .member(currentUser)
                .build();

        commentRepository.save(comment);

        return CommentDto.Response.from(comment);
    }

    @Transactional
    public CommentDto.Response update(Long commentId, MemberDetails member, CommentDto.Request commentRequestDto) throws IllegalAccessException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("No Comment For: " + commentId));

        Member currentUser = postService.findUser(member);
        postService.checkIsWriter(currentUser, member.getMember());

        comment.update(commentRequestDto.getContent());
        commentRepository.save(comment);

        return CommentDto.Response.from(comment);
    }

    @Transactional
    public void delete(Long commentId) {
        commentRepository.findById(commentId).orElseThrow(() ->
                new NoSuchElementException("No Comment For: " + commentId));
        commentRepository.deleteById(commentId);
    }
}
