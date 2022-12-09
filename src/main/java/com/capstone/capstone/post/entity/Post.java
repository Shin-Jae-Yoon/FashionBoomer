package com.capstone.capstone.post.entity;

import com.capstone.capstone.audit.Auditable;
import com.capstone.capstone.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Post extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "post_title", nullable = false, updatable = true, unique = false)
    private String postTitle;

    @Column(name = "post_content", nullable = false, updatable = true, unique = false)
    private String postContent;

    @ManyToOne
    @JoinColumn(name = "post_user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Column(name = "post_view", nullable = false, updatable = true, unique = false)
    private int postView;

    @Column(name = "post_like_count", nullable = false, updatable = true, unique = false)
    private int postLikeCount;

    @Column(name = "post_dislike_count", nullable = false, updatable = true, unique = false)
    private int postDislikeCount;

    @Column(name = "post_comment_count", nullable = false, updatable = true, unique = false)
    private int postCommentCount;
}
