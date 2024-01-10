package com.wonhyong.talk.board.dto;

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
public class PostRequestDto {

    private Long id;
    private String title;
    private String content;
    private Member member;
    private int view;

    public Post toEntity() {
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .member(member)
                .view(view)
                .build();
    }
}
