package com.capstone.capstone.comment.entity;

import com.capstone.capstone.audit.Auditable;
import com.capstone.capstone.cloth.entity.Cloth;
import com.capstone.capstone.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "CLOTH_ID")
    private Cloth cloth;

    @Column(nullable = false, updatable = true, unique = false)
    private String comment;
}
