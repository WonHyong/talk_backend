package com.wonhyong.talk.member.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class TokenResponse {

    private String name;
    private String accessToken;
    private Date expiration;

}
