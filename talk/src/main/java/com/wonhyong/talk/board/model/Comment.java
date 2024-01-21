package com.wonhyong.talk.board.model;

import com.wonhyong.talk.base.model.BaseTimeModel;
import com.wonhyong.talk.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "comments")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseTimeModel {

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void update(String content) {
        this.content = content;
    }

    public String getMemberName() {
        if (this.member == null) return "NONE";
        return member.getName();
    }
}
