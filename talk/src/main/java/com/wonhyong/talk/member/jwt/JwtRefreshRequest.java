package com.wonhyong.talk.member.jwt;

public class JwtRefreshRequest {

    private String refreshToken;

    public JwtRefreshRequest() {
        // 기본 생성자
    }

    public JwtRefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}