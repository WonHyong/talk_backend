package com.wonhyong.talk.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class JwtRefreshRequest {
    private String userName;
    private String refreshToken;
}