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

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping(value = "/new")
    public String create(@RequestBody Member member) {
        if (memberService.findByName(member.getName()) != null) {
            return "failed";
        }
        // 저장
        memberService.saveMember(member);

        return "Registration successful for user: " + member.getName();
    }

    @GetMapping
    public Iterable<Member> members() {
        return memberService.getAllMembers();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<MemberResponseDto> signin(@RequestBody MemberRequestDto request) throws Exception {
        return new ResponseEntity<>(memberService.login(request), HttpStatus.OK);
    }

    @Data
    static class CreateMemberRequest {
        private String name;
        private String password;
    }


    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
        private String passsword;
    }
}