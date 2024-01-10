package com.wonhyong.talk.board.dto;


import com.wonhyong.talk.board.entity.BaseTimeEntity;
import com.wonhyong.talk.board.entity.Comment;
import com.wonhyong.talk.board.entity.Post;
import com.wonhyong.talk.member.domain.Member;
import com.wonhyong.talk.member.dto.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
public class CommentResponseDto {

    private final Long id;
    private final String content;
    private final MemberResponseDto member;
    private final String createdDate, modifiedDate;

    public static CommentResponseDto from(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                null,
                comment.getCreatedDate().format(DateTimeFormatter.ofPattern(BaseTimeEntity.DATE_FORMAT)),
                comment.getModifiedDate().format(DateTimeFormatter.ofPattern(BaseTimeEntity.DATE_FORMAT))
        );
    }
}
