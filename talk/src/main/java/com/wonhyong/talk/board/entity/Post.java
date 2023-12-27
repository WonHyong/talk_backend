package com.wonhyong.talk.board.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "posts")
@Entity
@Getter
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

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}