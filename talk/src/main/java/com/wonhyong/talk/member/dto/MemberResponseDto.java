package com.wonhyong.talk.member.dto;


import com.wonhyong.talk.board.dto.CommentResponseDto;
import com.wonhyong.talk.board.dto.PostResponseDto;
import com.wonhyong.talk.board.entity.BaseTimeEntity;
import com.wonhyong.talk.board.entity.Post;
import com.wonhyong.talk.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Builder
public class MemberResponseDto {

    private String name;
    private String token;

}
