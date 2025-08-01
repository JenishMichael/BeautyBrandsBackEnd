package com.java.BeautyBrandsBE.repository;

import com.java.BeautyBrandsBE.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {

    List<SubCategory> findByCategoryCategoryId(Long categoryId);
    List<SubCategory>  findBySubCategoryActiveTrue();
    Long countByCategoryCategoryId(Long categoryId);

    List<SubCategory> findByCategoryCategoryIdAndSubCategoryActiveTrue(Long categoryId);

    boolean existsByCategoryCategoryIdAndSubCategoryName(Long categoryId, String subCategoryName);

    List<SubCategory> findBySubCategoryNameContainingIgnoreCase(String keyword);


}
