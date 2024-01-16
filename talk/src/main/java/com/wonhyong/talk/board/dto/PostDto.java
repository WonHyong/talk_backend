package com.wonhyong.talk.board.dto;

import com.wonhyong.talk.base.dto.BaseTimeDto;
import com.wonhyong.talk.board.model.Post;
import lombok.Getter;

@Getter
public class PostDto extends BaseTimeDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final int like;
    private final int view;
    private final int numComment;

    private PostDto(Post post) {
        super(post);    // set BaseTimeDto

        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getMemberName();
        this.like = post.getLikeNum();
        this.view = post.getView();
        this.numComment = post.getCommentNum();
    }

    private PostDto(Long id, String title, String content, String writer, int view, int like, int numComment, String createdDate, String modifiedDate) {
        super(createdDate, modifiedDate);    // set BaseTimeDto

        this.id = id;
        this.title = title;
        this.content = content;
        this.like = like;
        this.writer = writer;
        this.view = view;
        this.numComment = numComment;
    }

    public static PostDto from(Post post) {
        return new PostDto(post);
    }
}
