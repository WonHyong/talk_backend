package com.wonhyong.talk.board.dto;

import com.wonhyong.talk.base.dto.BaseTimeResponseDto;
import com.wonhyong.talk.board.model.Like;
import com.wonhyong.talk.board.model.Post;
import com.wonhyong.talk.member.dto.MemberDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


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
        private int view;
        private List<CommentDto.Response> comments;
        private List<MemberDto> likedMembers;

        private DetailResponse(Post post) {
            super(post);
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.writer = post.getWriter().getName();
            this.view = post.getView();

            this.comments = post.getComments().stream()
                    .map(CommentDto.Response::from)
                    .collect(Collectors.toList());

            this.likedMembers = post.getLikes().stream()
                    .map(Like::getMember)
                    .map(MemberDto::from)
                    .collect(Collectors.toList());
        }

        public static DetailResponse from(Post post) {
            return new DetailResponse(post);
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
    }
}