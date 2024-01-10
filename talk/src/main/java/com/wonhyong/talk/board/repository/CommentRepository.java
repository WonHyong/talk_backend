package com.wonhyong.talk.board.repository;

import com.wonhyong.talk.board.entity.Comment;
import com.wonhyong.talk.board.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
}
