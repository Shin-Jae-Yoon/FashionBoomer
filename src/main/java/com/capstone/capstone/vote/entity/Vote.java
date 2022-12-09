package com.capstone.capstone.vote.entity;

import com.capstone.capstone.audit.Auditable;
import com.capstone.capstone.member.entity.Member;
import com.capstone.capstone.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Vote extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id;

    @ManyToOne
    @JoinColumn(name = "vote_post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "vote_member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Column(name = "vote_type", nullable = false, updatable = true, unique = false)
    private int voteType;
}
