package com.capstone.capstone.member.entity;

import com.capstone.capstone.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 20)
    private String name;

    @Column(nullable = false, updatable = false, unique = false)
    private String email;

    @Column(length = 20)
    private String platform;

//    @Column(length = 100, nullable = false)
//    private String password;

//    @Column(length = 100, nullable = false)
//    private String phone;

//    @Enumerated(value = EnumType.STRING)
//    @Column(length = 20, nullable = false)
//    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;
//
//    @ElementCollection(fetch = FetchType.EAGER)
//    private List<String> roles = new ArrayList<>();

//    public Member(String email) {
//        this.email = email;
//    }

    public Member(String email) {
        this.email = email;
    }

//    public Member(String email, String name, String password, String phone) {
//        this.email = email;
//        this.name = name;
//        this.password = password;
//        this.phone = phone;
//    }
//
//    public enum MemberStatus {
//        MEMBER_ACTIVE("활동중"),
//        MEMBER_SLEEP("휴면 상태"),
//        MEMBER_QUIT("탈퇴 상태");
//
//        @Getter
//        private String status;
//
//        MemberStatus(String status) {
//            this.status = status;
//        }
//    }
//
//    public enum MemberRole {
//        ROLE_USER,
//        ROLE_ADMIN
//    }
}
