package com.java.BeautyBrandsBE.mapper;

import com.java.BeautyBrandsBE.dto.SubCategoryRequestDTO;
import com.java.BeautyBrandsBE.dto.SubCategoryResponseDTO;
import com.java.BeautyBrandsBE.model.Category;
import com.java.BeautyBrandsBE.model.SubCategory;

public class SubCategoryMapper {

    // Convert Request DTO to Entity
    public static SubCategory toEntity(SubCategoryRequestDTO dto, Category category) {
        SubCategory subCategory = new SubCategory();
        subCategory.setSubCategoryName(dto.getSubCategoryName());
        subCategory.setSubCategoryIconUrl(dto.getSubCategoryIconUrl());
        subCategory.setSubCategoryActive(dto.getSubCategoryActive());
        subCategory.setCategory(category);
        return subCategory;
    }

    // Convert Entity to Response DTO
    public static SubCategoryResponseDTO toResponseDTO(SubCategory entity) {
        SubCategoryResponseDTO dto = new SubCategoryResponseDTO();
        dto.setSubCategoryId(entity.getSubCategoryId());
        dto.setSubCategoryName(entity.getSubCategoryName());
        dto.setSubCategoryIconUrl(entity.getSubCategoryIconUrl());
        dto.setSubCategoryActive(entity.getSubCategoryActive());

        if (entity.getCategory() != null) {
            dto.setCategoryId(entity.getCategory().getCategoryId());
        }

        return dto;
    }
}
