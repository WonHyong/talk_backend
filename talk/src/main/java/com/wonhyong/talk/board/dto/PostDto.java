package com.wonhyong.talk.board.dto;

import com.wonhyong.talk.base.dto.BaseTimeDto;
import com.wonhyong.talk.board.model.Post;
import com.wonhyong.talk.member.domain.Member;
import lombok.Getter;

@Getter
public class PostDto extends BaseTimeDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final int like; //  음수면 현재 보고 있는 사용자가 이미 좋아요 한거임
    private final int view;
    private final int numComment;

    private PostDto(Post post, Member user) {
        super(post);    // set BaseTimeDto

        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getMemberName();
        this.view = post.getView();
        this.numComment = post.getCommentNum();

        // 현재 사용자 좋아요 여부
        int likeNum = post.getLikeNum();
        if (post.isAlreadyLiked(user)) {
            likeNum *= -1;
        }

        this.like = likeNum;
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

    public static PostDto from(Post post, Member user) {
        return new PostDto(post, user);
    }
}
