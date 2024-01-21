package com.wonhyong.talk.board.repository;

import com.wonhyong.talk.board.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    Slice<Post> findSliceBy(Pageable pageable);

    @Modifying
    @Query("update Post p set p.view = p.view + 1 where p.id = :id")
    void increaseView(@Param("id") Long id);
}
