package com.wonhyong.talk.member.repository;

import com.wonhyong.talk.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);
}
