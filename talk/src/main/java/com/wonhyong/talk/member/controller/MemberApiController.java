package com.wonhyong.talk.member.controller;

import com.wonhyong.talk.member.dto.MemberRequestDto;
import com.wonhyong.talk.member.dto.MemberResponseDto;
import com.wonhyong.talk.member.service.MemberService;
import com.wonhyong.talk.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@CrossOrigin
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping(value = "/new")
    public String create(@RequestBody MemberRequestDto memberRequestDto) {
        if (memberService.findByName(memberRequestDto.getName()).isPresent()) {
            return "failed";
        }
        // 저장
        memberService.saveMember(memberRequestDto);

        return "Registration successful for user: " + memberRequestDto.getName();
    }

    @GetMapping
    public Iterable<Member> members() {
        return memberService.getAllMembers();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<MemberResponseDto> signIn(@RequestBody MemberRequestDto request) throws Exception {
        return new ResponseEntity<>(memberService.login(request), HttpStatus.OK);
    }
}