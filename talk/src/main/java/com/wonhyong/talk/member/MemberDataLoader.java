package com.wonhyong.talk.member;

import com.wonhyong.talk.board.entity.Post;
import com.wonhyong.talk.board.repository.PostRepository;
import com.wonhyong.talk.member.domain.Member;
import com.wonhyong.talk.member.repository.MemberRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class MemberDataLoader {
    private final MemberRepository memberRepository;

    public MemberDataLoader(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostConstruct
    private void loadBoardData() {
        List<Member> sampleMembers = new ArrayList<>(10);

        for (int i = 0; i < 100; i++) {
            Member member = new Member();
            member.setName("test" + i);
            member.setPassword("1234");
            sampleMembers.add(member);
        }

        memberRepository.saveAll(sampleMembers);
    }
}
