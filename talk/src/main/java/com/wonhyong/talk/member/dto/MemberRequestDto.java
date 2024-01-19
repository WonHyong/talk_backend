package com.wonhyong.talk.member.dto;

import com.wonhyong.talk.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {

    private String name;
    private String password;
    private String email;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .password(password)
                .email(email)
                .build();
    }
}
