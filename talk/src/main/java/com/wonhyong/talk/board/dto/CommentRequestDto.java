package com.wonhyong.talk.board.dto;

import com.wonhyong.talk.board.entity.Comment;
import com.wonhyong.talk.board.entity.Post;
import com.wonhyong.talk.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {

    private Long id;
    private String content;
    private Member member;
    private Post post;

    public Comment toEntity() {
        return Comment.builder()
                .id(id)
                .content(content)
                .member(member)
                .post(post)
                .build();
    }
}
