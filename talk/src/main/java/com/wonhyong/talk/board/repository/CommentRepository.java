package com.wonhyong.talk.board.repository;

import com.wonhyong.talk.board.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    int countAllByPost_Id(Long postId);

    List<Comment> findAllByPost_Id(Long postId);
}
