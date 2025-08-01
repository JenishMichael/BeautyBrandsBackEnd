package com.java.BeautyBrandsBE.mapper;

import com.java.BeautyBrandsBE.dto.CategoryRequestDTO;
import com.java.BeautyBrandsBE.dto.CategoryResponseDTO;
import com.java.BeautyBrandsBE.model.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryRequestDTO categoryRequestDTO){

        Category category = new Category();
        category.setCategoryName(categoryRequestDTO.getCategoryName());
        category.setCategoryIconUrl(categoryRequestDTO.getCategoryIconUrl());
        category.setCategoryActive(categoryRequestDTO.getCategoryActive());

        return category;
    }

    public static CategoryResponseDTO toDto(Category category){
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setCategoryId(category.getCategoryId());
        categoryResponseDTO.setCategoryName(category.getCategoryName());
        categoryResponseDTO.setCategoryIconUrl(category.getCategoryIconUrl());
        categoryResponseDTO.setCategoryActive(category.getCategoryActive());

        return categoryResponseDTO;

    }
}
