package com.wonhyong.talk.board.dto;

import com.wonhyong.talk.base.dto.BaseTimeDto;
import com.wonhyong.talk.board.model.Comment;
import com.wonhyong.talk.board.model.Post;
import com.wonhyong.talk.member.domain.Member;
import lombok.Getter;

@Getter
public class CommentDto extends BaseTimeDto {

    private final Long id;
    private final String content;
    private final String writer;

    private CommentDto(Comment comment) {
        super(comment); // set BaseTimeDto

        this.id = comment.getId();
        this.content = comment.getContent();
        this.writer = comment.getMemberName();
    }

    private CommentDto(Long id, String content, String writer, String createdDate, String modifiedDate) {
        super(createdDate, modifiedDate);

        this.id = id;
        this.content = content;
        this.writer = writer;
    }

    public Comment toModel(Member member, Post post) {
        return Comment.builder()
                .id(id)
                .post(post)
                .content(content)
                .member(member)
                .build();
    }

    public static CommentDto from(Comment comment) {
        return new CommentDto(comment);
    }
}
