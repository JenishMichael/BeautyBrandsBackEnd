package com.java.BeautyBrandsBE.service;

import com.java.BeautyBrandsBE.dto.SubCategoryRequestDTO;
import com.java.BeautyBrandsBE.dto.SubCategoryResponseDTO;

import java.util.List;

public interface SubCategoryService {
    // Create
    SubCategoryResponseDTO createSubCategory(SubCategoryRequestDTO requestDTO);

    // Read
    List<SubCategoryResponseDTO> getAllSubCategories();
    SubCategoryResponseDTO getSubCategoryById(Long subCategoryId);
    List<SubCategoryResponseDTO> getSubCategoriesByCategoryId(Long categoryId);
    List<SubCategoryResponseDTO> getActiveSubCategories();
    List<SubCategoryResponseDTO> getActiveSubCategoriesByCategoryId(Long categoryId);

    // Update
    SubCategoryResponseDTO updateSubCategory(Long subCategoryId, SubCategoryRequestDTO requestDTO);

    // Soft Delete / Reactivate
    void deactivateSubCategory(Long subCategoryId);
    void activateSubCategory(Long subCategoryId);

    // Search
    List<SubCategoryResponseDTO> searchSubCategories(String keyword);







}
