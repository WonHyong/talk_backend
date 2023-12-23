package com.wonhyong.talk.board.repository;

import com.wonhyong.talk.board.model.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BoardRepository extends PagingAndSortingRepository<Board, Long> {
    List<Board> findByTitleContaining(String title);

    Slice<Board> findSliceBy(Pageable pageable);
}
