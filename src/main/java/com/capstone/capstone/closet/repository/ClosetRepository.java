package com.capstone.capstone.closet.repository;

import com.capstone.capstone.closet.entity.Closet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClosetRepository extends JpaRepository<Closet, Integer> {
    List<Closet> findByMember_MemberId(long id);
    // Select * from Closet where Member.memberId = member_id and Cloth.Id = cloth_id
    Optional<Closet> findByMember_MemberIdAndCloth_Id(long member_id, int cloth_id);
}
