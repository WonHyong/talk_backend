package com.wonhyong.talk.board.dto;

import com.wonhyong.talk.base.dto.BaseTimeResponseDto;
import com.wonhyong.talk.board.model.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public class PostDto {

    @Getter
    @RequiredArgsConstructor
    public static class Request {

        @NotBlank(message = "게시글 제목이 필요합니다.")
        private final String title;

        private final String content;
    }

    @Getter
    @AllArgsConstructor
    public static class DetailResponse extends BaseTimeResponseDto {
        private Long id;
        private String title;
        private String content;
        private String writer;
        private int like;
        private int view;
        private int numComment;

        private DetailResponse(Post post, int like, int numComment) {
            super(post);
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.writer = post.getWriter().getName();
            this.like = like;
            this.view = post.getView();
            this.numComment = numComment;
        }

        public static DetailResponse from(Post post, int like, int numComment) {
            return new DetailResponse(post, like, numComment);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class ListResponse extends BaseTimeResponseDto {
        private Long id;
        private String title;
        private String writer;
        private int like;
        private int view;
        private int numComment;

        private ListResponse(Post post, int like, int numComment) {
            super(post);
            this.id = post.getId();
            this.title = post.getTitle();
            this.writer = post.getWriter().getName();
            this.like = like;
            this.view = post.getView();
            this.numComment = numComment;
        }

        public static ListResponse from(Post post, int like, int numComment) {
            return new ListResponse(post, like, numComment);
        }

        public static ListResponse from(Post post) {
            return new ListResponse(post, post.getLikes().size(), post.getComments().size());
        }
    }
}