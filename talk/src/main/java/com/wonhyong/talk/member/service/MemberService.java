package com.wonhyong.talk.member.service;

import com.wonhyong.talk.member.domain.Member;
import com.wonhyong.talk.member.domain.MemberDetails;
import com.wonhyong.talk.member.dto.MemberRequestDto;
import com.wonhyong.talk.member.dto.TokenResponse;
import com.wonhyong.talk.member.repository.MemberRepository;
import com.wonhyong.talk.security.jwt.JwtProvider;
import com.wonhyong.talk.security.jwt.JwtTokenService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Triple;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtTokenService jwtTokenService;
    private final MemberDetailsService memberDetailsService;

    public void saveMember(MemberRequestDto memberRequestDto) {
        Member member = memberRequestDto.toEntity();
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

    public Optional<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
  
    public Iterable<TokenResponse> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(member -> TokenResponse.builder()
                    .name(member.getName())
                    .build())
                .collect(Collectors.toList());
    }

    public Optional<Member> getMemberById(Long userId) {
        return memberRepository.findById(userId);
    }

    public void deleteMember(Long userId) {
        memberRepository.deleteById(userId);
    }

    public boolean isMemberEquals(@NonNull Member a, @NonNull Member b) {
        return a.getName().equals(b.getName());
    }

    public Triple login(MemberRequestDto request) {
        Member member = memberRepository.findByName(request.getName()).orElseThrow(() ->
                new BadCredentialsException("잘못된 계정정보입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }

        MemberDetails memberDetails = (MemberDetails) memberDetailsService.loadUserByUsername(member.getName());

        final String accessToken = jwtProvider.generateAccessToken(memberDetails);
        final String refreshTokenValue = jwtProvider.generateRefreshToken(memberDetails);

        jwtTokenService.saveRefreshToken(memberDetails.getUsername(), refreshTokenValue);

        return new Triple(accessToken, refreshTokenValue, jwtProvider.extractExpiration(accessToken));

    }

    public TokenResponse refreshAccessToken(String userName, String refreshTokenValue) {
        if (jwtProvider.validateToken(refreshTokenValue)) {
            String tokenUserName = jwtProvider.getUserPk(refreshTokenValue);
            String refreshToken = jwtTokenService.getRefreshToken(userName);

            final String accessToken = jwtProvider.generateAccessToken((MemberDetails) memberDetailsService.loadUserByUsername(tokenUserName));

            if (userName.equals(tokenUserName) && refreshToken.equals(refreshTokenValue)) {
                return TokenResponse.builder()
                        .name(userName)
                        .accessToken(accessToken)
                        .expiration(jwtProvider.extractExpiration(accessToken))
                        .build();
            }
        }

        throw new RuntimeException("Invalid refresh token");
    }
}
