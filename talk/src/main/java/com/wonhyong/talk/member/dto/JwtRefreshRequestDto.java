package com.wonhyong.talk.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class JwtRefreshRequestDto {

    private String userName;
    private String refreshToken;

    public JwtRefreshRequestDto() {
        // 기본 생성자
    }

    public JwtRefreshRequestDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}