package com.wonhyong.talk.board.dto;

import com.wonhyong.talk.base.dto.BaseTimeResponseDto;
import com.wonhyong.talk.board.domain.Comment;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CommentDto {

    @Getter
    @RequiredArgsConstructor
    public static class Request {
        @NotBlank(message = "댓글 내용이 필요합니다.")
        private final String content;
    }

    @Getter
    @AllArgsConstructor
    public static class Response extends BaseTimeResponseDto {
        private final Long id;
        private final String content;
        private final String writer;

        private Response(Comment comment) {
            super(comment); // set BaseTimeDto

            this.id = comment.getId();
            this.content = comment.getContent();
            this.writer = comment.getWriter().getName();
        }

        public static Response from(Comment comment) {
            return new Response(comment);
        }
    }
}
