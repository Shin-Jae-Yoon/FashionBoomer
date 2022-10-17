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
    @Query(value = "SELECT c FROM CLOSET c WHERE c.member.memberId = :id")
    Page<Closet> findByMemberId(long id, PageRequest pageRequest);
}
