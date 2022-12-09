package com.capstone.capstone.cloth.repository;

import com.capstone.capstone.cloth.entity.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClothRepository extends JpaRepository<Cloth, Integer> {
    // Select * from Cloth where Id = id
    Optional<Cloth> findById(int id);
    // Select * from Cloth where Category = category and Details = details
    Optional<List<Cloth>> findByCategoryAndDetails(String category, String detail);
    // Select * from Cloth where Category = category and Details = details and path like %"/{id}."%
    Optional<Cloth> findByCategoryAndDetailsAndPathContaining(String category, String detail, String path);
}
