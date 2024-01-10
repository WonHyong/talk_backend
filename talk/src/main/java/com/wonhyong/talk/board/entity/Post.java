package com.wonhyong.talk.board.entity;

import com.wonhyong.talk.member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "posts")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseView() {
        this.view += 1;
    }
}
