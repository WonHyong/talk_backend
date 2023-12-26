package com.wonhyong.talk.board.repository;

import com.wonhyong.talk.board.dto.PostResponseDto;
import com.wonhyong.talk.board.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

    Slice<Post> findSliceBy(Pageable pageable);
}
