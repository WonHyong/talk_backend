package com.wonhyong.talk.board.dto;

import com.wonhyong.talk.board.entity.Post;
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
    private int view;

    public Post toEntity() {
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .view(view)
                .build();
    }
}
