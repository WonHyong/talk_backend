package com.wonhyong.talk.board.entity;

import com.wonhyong.talk.member.domain.Member;
import lombok.*;

import javax.persistence.*;

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
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setMappingMember(Member member) {
        this.member = member;
        member.getBoards().add(this);
    }
}
