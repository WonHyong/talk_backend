package com.wonhyong.talk.member;

import com.wonhyong.talk.member.domain.Member;
import com.wonhyong.talk.member.domain.Role;
import com.wonhyong.talk.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberDataLoader {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void loadAdminUserData() {
        memberRepository.deleteAll();

        Member admin = Member.builder()
                .name("ADMIN")
                .password(passwordEncoder.encode("1234"))
                .role(Role.ADMIN)
                .build();

        memberRepository.save(admin);
    }
}
