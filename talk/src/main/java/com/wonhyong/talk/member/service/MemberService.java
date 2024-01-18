package com.wonhyong.talk.member.service;

import com.wonhyong.talk.board.dto.CommentDto;
import com.wonhyong.talk.board.dto.PostDto;
import com.wonhyong.talk.board.model.Like;
import com.wonhyong.talk.member.domain.Member;
import com.wonhyong.talk.member.domain.RefreshToken;
import com.wonhyong.talk.member.dto.MemberRequestDto;
import com.wonhyong.talk.member.dto.MemberResponseDto;
import com.wonhyong.talk.member.jwt.JwtProvider;
import com.wonhyong.talk.member.repository.MemberRepository;
import com.wonhyong.talk.member.repository.RefreshTokenRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final RefreshTokenRepository refreshTokenRepository;

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
  
    public Iterable<MemberResponseDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(member -> MemberResponseDto.builder()
                    .name(member.getName())
                    .build())
                .collect(Collectors.toList());
    }

    public Iterable<PostDto> getLikePosts(@NonNull String name) {
        Member user = memberRepository.findByName(name).orElseThrow(() ->
                new UsernameNotFoundException("NO USER FOR " + name));

        return user.getLikes().stream()
                .map(Like::getLikeTo)
                .map(post -> PostDto.from(post, user))
                .collect(Collectors.toList());
    }

    public Iterable<PostDto> getWritePosts(String name) {
        Member user = memberRepository.findByName(name).orElseThrow(() ->
                new UsernameNotFoundException("NO USER FOR " + name));

        return user.getPosts().stream()
                .map(post -> PostDto.from(post, user))
                .collect(Collectors.toList());
    }

    public Iterable<CommentDto> getWriteComments(String name) {
        Member user = memberRepository.findByName(name).orElseThrow(() ->
                new UsernameNotFoundException("NO USER FOR " + name));

        return user.getComments().stream()
                .map(CommentDto::from)
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

    public MemberResponseDto login(MemberRequestDto request) throws Exception {
        Member member = memberRepository.findByName(request.getName()).orElseThrow(() ->
                new BadCredentialsException("잘못된 계정정보입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }

        final String accessToken = jwtProvider.createToken(member.getId(), member.getName(), member.getRole());
        final String refreshTokenValue = jwtProvider.generateRefreshToken(member.getId(), member.getName(), member.getRole());

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setTokenValue(refreshTokenValue);
        refreshToken.setMember(member);
        refreshToken.setCreatedAt(jwtProvider.extractIssuedAt(refreshTokenValue));
        refreshToken.setExpirationTime(jwtProvider.extractExpiration(refreshTokenValue));
        refreshTokenRepository.save(refreshToken);

        return MemberResponseDto.builder()
                .name(member.getName())
                .accessToken(accessToken)
                .refreshToken(refreshTokenValue)
                .expiration(jwtProvider.extractExpiration(accessToken))
                .build();

    }

    public MemberResponseDto refreshAccessToken(String refreshTokenValue) {
        if (jwtProvider.validateToken(refreshTokenValue)) {
            String username = jwtProvider.getUserPk(refreshTokenValue);
            RefreshToken refreshTokenEntity = refreshTokenRepository.findByTokenValue(refreshTokenValue);

            Member member = refreshTokenEntity.getMember();

            final String accessToken = jwtProvider.createToken(member.getId(), member.getName(), member.getRole());

            if (refreshTokenEntity != null && member.getName().equals(username)) {
                return MemberResponseDto.builder()
                        .name(member.getName())
                        .accessToken(accessToken)
                        .refreshToken(refreshTokenValue)
                        .expiration(jwtProvider.extractExpiration(accessToken))
                        .build();
            }
        }

        throw new RuntimeException("Invalid refresh token");
    }
}
