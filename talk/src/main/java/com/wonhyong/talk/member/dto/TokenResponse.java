package com.wonhyong.talk.member.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Builder
public class TokenResponse {

    private String name;
    private String accessToken;
    private String refreshToken;
    private Date expiration;

}
