package com.capstone.capstone.vote.repository;

import com.capstone.capstone.vote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
    List<Vote> findByPost_Id(int postId);
    List<Vote> findByMember_MemberId(Long member_memberId);
    Optional<Vote> findByMember_MemberIdAndPost_Id(long memberId, int postId);

    Boolean existsByMember_MemberIdAndPost_Id(long memberId, int postId);
}

