package com.wonhyong.talk.board.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    @Setter
    private String title;

    @Column(name = "description")
    @Setter
    private String description;

    public Board() {
    }

    public Board(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Board [id=" + id + ", title=" + title + ", desc=" + description + "]";
    }

}
