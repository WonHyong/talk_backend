package com.wonhyong.talk.board.model;

import com.wonhyong.talk.base.model.BaseTimeModel;
import com.wonhyong.talk.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "likes")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like extends BaseTimeModel {

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post likeTo;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
