package com.wonhyong.talk.board.dto;


import com.wonhyong.talk.board.entity.BaseTimeEntity;
import com.wonhyong.talk.board.entity.Post;
import com.wonhyong.talk.member.domain.Member;
import com.wonhyong.talk.member.dto.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class PostResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String memberName;
    private final List<CommentResponseDto> comments;
    private final String createdDate, modifiedDate;
    private final int view;

    public static PostResponseDto from(Post post) {
        Member member = post.getMember();
        String mName = (member == null)? "EMPTY" : member.getName();
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                mName,
                post.getComments().stream().map(CommentResponseDto::from).collect(Collectors.toList()),
                post.getCreatedDate().format(DateTimeFormatter.ofPattern(BaseTimeEntity.DATE_FORMAT)),
                post.getModifiedDate().format(DateTimeFormatter.ofPattern(BaseTimeEntity.DATE_FORMAT)),
                post.getView()
        );
    }
}