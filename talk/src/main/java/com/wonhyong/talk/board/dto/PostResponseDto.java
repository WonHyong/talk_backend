package com.wonhyong.talk.board.dto;


import com.wonhyong.talk.board.entity.Post;
import com.wonhyong.talk.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
public class PostResponseDto {

    private static final String DATE_FORMAT = "yyyy.mm.dd HH:mm";

    private final Long id;
    private final String title;
    private final String content;
    private final Member member;
    private final String createdDate, modifiedDate;
    private final int view;

    public static PostResponseDto from(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember(),
                post.getCreatedDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                post.getModifiedDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                post.getView()
        );
    }
}
