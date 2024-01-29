package com.wonhyong.talk.member.dto;

import com.wonhyong.talk.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class MemberDto {
    private String name;
    private String email;

    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }
}
