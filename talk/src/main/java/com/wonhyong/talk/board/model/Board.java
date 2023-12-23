package com.wonhyong.talk.board.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Getter
@Entity
@Table(name = "boards")
@RequiredArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "title", "createdTime", "updatedTime", "content"})
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @NonNull
    @Column
    @Setter
    private String title;

    @NonNull
    @Column
    @Setter
    private String content;

    @CreationTimestamp
    @Column(name = "created_time")
    @JsonProperty("created_time")
    private Date createdTime;

    @UpdateTimestamp
    @Column(name = "updated_time")
    @JsonProperty("updated_time")
    private Date updatedTime;
}
