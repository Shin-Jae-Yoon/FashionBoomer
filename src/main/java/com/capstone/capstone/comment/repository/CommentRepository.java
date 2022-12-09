package com.capstone.capstone.comment.repository;

import com.capstone.capstone.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByMember_MemberId(long member_id);
    List<Comment> findByPost_Id(int cloth_id);
}
