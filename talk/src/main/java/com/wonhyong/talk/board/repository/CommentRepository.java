package com.wonhyong.talk.board.repository;

import com.wonhyong.talk.board.model.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
}
