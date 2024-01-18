package com.wonhyong.talk.board.model;

import com.wonhyong.talk.base.model.BaseTimeModel;
import com.wonhyong.talk.member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "posts")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeModel {

    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // todo: 개수만 필요함 -> 최적화 가능할듯?
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "likeTo", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Like> likes = new HashSet<>();

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    @Builder
    public Post(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getMemberName() {
        if (member == null) return "NONE";
        return member.getName();
    }

    public int getLikeNum() {
        return likes.size();
    }

    // if post is liked by viewer, return like * -1
    public int getLikeNum(Member user) {
        if (isAlreadyLiked(user)) return getLikeNum() * -1;
        return getLikeNum();
    }

    public int getCommentNum() {
        return comments.size();
    }

    public boolean isAlreadyLiked(Member user) {
        return likes.stream().noneMatch(like ->
                        like.getMember().getName().equals(user.getName()));
    }
}
