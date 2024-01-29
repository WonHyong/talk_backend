package com.wonhyong.talk.member.domain;

import com.wonhyong.talk.board.model.Comment;
import com.wonhyong.talk.board.model.Like;
import com.wonhyong.talk.board.model.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String name;

    private String password;

    @Email
    private String email;

    @Column(name = "authority")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private final List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private final List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private final List<Comment> comments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o instanceof Member) return ((Member)o).id.equals(id);
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
