package com.capstone.capstone.closet.entity;

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
public class Closet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "CLOTH_ID")
    private Cloth cloth;
}
