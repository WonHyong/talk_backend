package com.wonhyong.talk.member.controller;

import com.wonhyong.talk.member.service.MemberService;
import com.wonhyong.talk.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping(value = "/new")
    public CreateMemberResponse create(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        member.setPassword(request.getPassword());
        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    @GetMapping
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName(), m.getPassword()))
                .collect(Collectors.toList());
        return new Result(collect);
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