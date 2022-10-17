package com.capstone.capstone.cloth.repository;

import com.capstone.capstone.cloth.entity.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClothRepository extends JpaRepository<Cloth, Integer> {
    Optional<Cloth> findById(int id);
}
