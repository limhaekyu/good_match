package com.example.good_match.domain.category.repository;

import com.example.good_match.domain.category.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    Optional<SubCategory> findAllByCategoryId(Long categoryId);
}
