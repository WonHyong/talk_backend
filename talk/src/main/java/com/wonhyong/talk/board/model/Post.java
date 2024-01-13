package com.wonhyong.talk.board.model;

import com.wonhyong.talk.base.model.BaseTimeModel;
import com.wonhyong.talk.member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "posts")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    //TODO title and content empty check
    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    public void update(String title, String content) throws IllegalArgumentException {
        if (title == null || title.isEmpty() || content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Require NOT EMPTY Title and Content");
        }
        this.title = title;
        this.content = content;
    }

    public String getMemberName() {
        if (this.member == null) return "NONE";
        return member.getName();
    }

    public int getCommentNum() {
        if (this.comments == null) return 0;
        return comments.size();
    }
}
