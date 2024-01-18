package com.wonhyong.talk.member.controller;

import com.wonhyong.talk.board.dto.CommentDto;
import com.wonhyong.talk.board.dto.PostDto;
import com.wonhyong.talk.member.domain.MemberDetails;
import com.wonhyong.talk.member.dto.MemberDto;
import com.wonhyong.talk.member.dto.MemberRequestDto;
import com.wonhyong.talk.member.dto.MemberResponseDto;
import com.wonhyong.talk.member.dto.JwtRefreshRequestDto;
import com.wonhyong.talk.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@CrossOrigin
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping(value = "/new")
    public ResponseEntity<String> create(@RequestBody MemberRequestDto memberRequestDto) {
        if (memberService.findByName(memberRequestDto.getName()).isPresent()) {
            return new ResponseEntity<>("이미 사용중인 아이디입니다.", HttpStatus.CONFLICT);
        } else if (memberService.findByEmail(memberRequestDto.getEmail()).isPresent()) {
            return new ResponseEntity<>("이미 사용중인 이메일입니다.", HttpStatus.CONFLICT);
        }
        // 저장
        memberService.saveMember(memberRequestDto);

        return new ResponseEntity<>("회원가입 성공!", HttpStatus.OK);
    }

    @GetMapping
    public Iterable<MemberResponseDto> members() {
        return memberService.getAllMembers();
    }

    @GetMapping("/likes")
    public Iterable<PostDto> getLikedPosts(@RequestParam String name) {
        return memberService.getLikePosts(name);
    }

    @GetMapping("/posts")
    public Iterable<PostDto> getWrotePosts(@RequestParam String name) {
        return memberService.getWritePosts(name);
    }

    @GetMapping("/comments")
    public Iterable<CommentDto> getWroteComments(@RequestParam String name) {
        return memberService.getWriteComments(name);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<MemberResponseDto> signIn(@RequestBody MemberRequestDto request) throws Exception {
        return new ResponseEntity<>(memberService.login(request), HttpStatus.OK);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<MemberResponseDto> refreshAuthenticationToken(@RequestBody JwtRefreshRequestDto refreshRequest) throws Exception{
        try {
            return new ResponseEntity<>(memberService.refreshAccessToken(refreshRequest.getUserName(), refreshRequest.getRefreshToken()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/mypage")
    public ResponseEntity<MemberDto> myPage(@AuthenticationPrincipal MemberDetails member) {
        return new ResponseEntity<>(MemberDto.builder().
                name(member.getUsername())
                .email(member.getEmail()).build(), HttpStatus.OK);
    }
}