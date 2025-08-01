package com.java.BeautyBrandsBE.repository;

import com.java.BeautyBrandsBE.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByCategoryActiveTrue();
    List<Category> findByCategoryActiveFalse();

    Long countByCategoryActiveTrue();
    Long countByCategoryActiveFalse();

    //
    Optional<Category> findByCategoryName(String categoryName);

}
