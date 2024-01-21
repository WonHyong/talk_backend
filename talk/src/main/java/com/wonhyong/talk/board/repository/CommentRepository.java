package com.wonhyong.talk.board.repository;

import com.wonhyong.talk.board.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
