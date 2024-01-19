package com.wonhyong.talk.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class MemberDto {

    private String name;
    private String email;
}
