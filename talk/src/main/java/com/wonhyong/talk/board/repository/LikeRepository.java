package com.wonhyong.talk.board.repository;

import com.wonhyong.talk.board.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    int countAllByPost_Id(Long postId);

    List<Like> findAllByPost_Id(Long postId);

    boolean existsByPost_IdAndMember_Id(Long postId, Long memberId);
}
