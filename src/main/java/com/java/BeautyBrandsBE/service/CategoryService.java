package com.java.BeautyBrandsBE.service;

import com.java.BeautyBrandsBE.dto.CategoryRequestDTO;
import com.java.BeautyBrandsBE.dto.CategoryResponseDTO;
import com.java.BeautyBrandsBE.model.Category;

import java.util.List;

public interface CategoryService {

    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);
    CategoryResponseDTO updateCategory(Long categoryId, CategoryRequestDTO categoryRequestDTO);

    List<CategoryResponseDTO> getAllCategories();
    List<CategoryResponseDTO> getActiveCategories();
    List<CategoryResponseDTO> getInactiveCategories();

    Long countAllCategories();
    Long countActiveCategories();
    Long countInactiveCategories();

    CategoryResponseDTO activateCategory(long id);
    CategoryResponseDTO deactivateCategory(long id);


    CategoryResponseDTO getCategoryById(Long id);


}
