package com.wonhyong.talk.board.model;

import com.wonhyong.talk.base.domain.BaseTimeDomain;
import com.wonhyong.talk.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "posts")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member writer;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Like> likes = new ArrayList<>();

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    @Builder
    public Post(String title, String content, Member writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public Post(Long id) {
        this.id = id;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
