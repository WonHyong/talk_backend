package com.wonhyong.talk.board.repository;

import com.wonhyong.talk.board.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
