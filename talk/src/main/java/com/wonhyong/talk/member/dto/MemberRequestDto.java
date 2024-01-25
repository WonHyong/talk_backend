package com.wonhyong.talk.member.dto;

import com.wonhyong.talk.member.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {

    @NotBlank(message = "아이디는 필수 값 입니다.")
    private String name;

    @NotBlank(message = "비밀번호는 필수 값 입니다.")
    private String password;

    @NotBlank(message = "이메일은 필수 값 입니다.")
    @Email
    private String email;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .password(password)
                .email(email)
                .build();
    }
}
