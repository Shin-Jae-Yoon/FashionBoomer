package com.capstone.capstone.post.repository;

import com.capstone.capstone.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByMember_MemberId(long memberId);
}
