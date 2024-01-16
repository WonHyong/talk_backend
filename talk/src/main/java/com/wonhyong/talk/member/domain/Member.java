package com.wonhyong.talk.member.domain;

import com.wonhyong.talk.board.model.Like;
import com.wonhyong.talk.board.model.Post;
import lombok.*;

import javax.persistence.*;
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

    @Column(name = "authority")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Like> likes = new ArrayList<>();
}
