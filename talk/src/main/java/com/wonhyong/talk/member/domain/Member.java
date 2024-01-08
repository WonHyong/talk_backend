package com.wonhyong.talk.member.domain;

import com.wonhyong.talk.board.entity.Post;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Address;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String password;

    @OneToMany(mappedBy = "member")
    private List<Post> boards = new ArrayList<>();
}
