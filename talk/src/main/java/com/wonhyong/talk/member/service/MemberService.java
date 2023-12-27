package com.wonhyong.talk.member.service;

import com.wonhyong.talk.member.domain.Member;
import com.wonhyong.talk.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveMember(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

    public Member findByName(String name) {
        return memberRepository.findByName(name);
    }
    public Iterable<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(Long userId) {
        return memberRepository.findById(userId);
    }

    public void deleteMember(Long userId) {
        memberRepository.deleteById(userId);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Member member = memberRepository.findByName(username);
//        if (member == null) {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//
//        return User.withUsername(username)
//                .password(member.getPassword())
//                .roles("USER")
//                .build();
//    }

}
