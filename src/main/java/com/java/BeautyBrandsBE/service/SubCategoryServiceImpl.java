package com.java.BeautyBrandsBE.service;

import com.java.BeautyBrandsBE.dto.SubCategoryRequestDTO;
import com.java.BeautyBrandsBE.dto.SubCategoryResponseDTO;
import com.java.BeautyBrandsBE.exception.CategoryNotFoundException;
import com.java.BeautyBrandsBE.exception.SubCategoryAlreadyPresentException;
import com.java.BeautyBrandsBE.exception.SubCategoryNotFoundException;
import com.java.BeautyBrandsBE.exception.SubCategoryStatusException;
import com.java.BeautyBrandsBE.mapper.SubCategoryMapper;
import com.java.BeautyBrandsBE.model.Category;
import com.java.BeautyBrandsBE.model.SubCategory;
import com.java.BeautyBrandsBE.repository.CategoryRepository;
import com.java.BeautyBrandsBE.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public SubCategoryResponseDTO createSubCategory(SubCategoryRequestDTO requestDTO) {
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(()->new CategoryNotFoundException("Not Found any category with ID "+requestDTO.getCategoryId()));

        if(subCategoryRepository.existsByCategoryCategoryIdAndSubCategoryName
                (requestDTO.getCategoryId(), requestDTO.getSubCategoryName())){
            throw new SubCategoryAlreadyPresentException(
                    "A subcategory named '" + requestDTO.getSubCategoryName() + "' already exists in the category '" + category.getCategoryName() + "' (Category ID: " + requestDTO.getCategoryId() + ").");

        }

        SubCategory savedSubCategory = subCategoryRepository.save(SubCategoryMapper.toEntity(requestDTO,category));

        return SubCategoryMapper.toResponseDTO(savedSubCategory);
    }

    @Override
    public List<SubCategoryResponseDTO> getAllSubCategories() {
        List<SubCategory> subCategoriesEntity =  subCategoryRepository.findAll();
        if(subCategoriesEntity.isEmpty()){
            throw new SubCategoryNotFoundException("No subcategories found in the database. Please add at least one.");
            }

        List<SubCategoryResponseDTO> subCategoryResponseDTOS = new ArrayList<>();

        for(SubCategory subCategoryEntity : subCategoriesEntity ){
            subCategoryResponseDTOS.add(SubCategoryMapper.toResponseDTO(subCategoryEntity));

        }
        return subCategoryResponseDTOS;
    }

    @Override
    public List<SubCategoryResponseDTO> getActiveSubCategories() {
        // Step 1: Fetch all subcategories where subCategoryActive is true
        List<SubCategory> activeSubCategories = subCategoryRepository.findBySubCategoryActiveTrue();

        // Step 2: If no active subcategories found, throw custom exception
        if (activeSubCategories.isEmpty()) {
            throw new SubCategoryNotFoundException("No active subcategories found.");
        }

        // Step 3: Prepare response list
        List<SubCategoryResponseDTO> responseDTOList = new ArrayList<>();

        // Step 4: Convert each SubCategory entity to response DTO
        for (SubCategory subCategory : activeSubCategories) {
            SubCategoryResponseDTO dto = SubCategoryMapper.toResponseDTO(subCategory);
            responseDTOList.add(dto);
        }

        // Step 5: Return the final list
        return responseDTOList;
    }


    @Override
    public SubCategoryResponseDTO getSubCategoryById(Long subCategoryId) {
        // Step 1: Find the subcategory by ID or throw an exception if not found
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new SubCategoryNotFoundException("Subcategory not found with ID: " + subCategoryId));

        // Step 2: Convert the entity to DTO using the mapper
        return SubCategoryMapper.toResponseDTO(subCategory);
    }


    @Override
    public List<SubCategoryResponseDTO> getSubCategoriesByCategoryId(Long categoryId) {

        // Step 1: Check if the category exists
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(
                        "No category found with ID: " + categoryId + ". Please provide a valid Category ID to fetch subcategories."
                ));

        // Step 2: Fetch subcategories belonging to the given category
        List<SubCategory> subCategories = subCategoryRepository.findByCategoryCategoryId(categoryId);

        // Step 3: If no subcategories found, throw an exception
        if (subCategories.isEmpty()) {
            throw new SubCategoryNotFoundException(
                    "No subcategories found under the category: " + category.getCategoryName() + " (ID: " + categoryId + ")."
            );
        }

        // Step 4: Convert each SubCategory entity to a response DTO
        List<SubCategoryResponseDTO> subCategoryResponseDTOList = new ArrayList<>();
        for (SubCategory subCategory : subCategories) {
            subCategoryResponseDTOList.add(SubCategoryMapper.toResponseDTO(subCategory));
        }

        // Step 5: Return the list of DTOs
        return subCategoryResponseDTOList;
    }



    @Override
    public List<SubCategoryResponseDTO> getActiveSubCategoriesByCategoryId(Long categoryId) {
        // Step 1: Check if the category exists
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(
                        "No category found with ID: " + categoryId + ". Please provide a valid Category ID to fetch active subcategories."
                ));

        // Step 2: Fetch only active subcategories for this category
        List<SubCategory> activeSubCategoryListBasedOnCategoryId =
                subCategoryRepository.findByCategoryCategoryIdAndSubCategoryActiveTrue(categoryId);

        // Step 3: If no active subcategories found, throw an exception
        if (activeSubCategoryListBasedOnCategoryId.isEmpty()) {
            throw new SubCategoryNotFoundException(
                    "No active subcategories found under the category: " + category.getCategoryName() + " (ID: " + categoryId + ")."
            );
        }

        // Step 4: Convert SubCategory entities to Response DTOs
        List<SubCategoryResponseDTO> responseDTOList = new ArrayList<>();
        for (SubCategory subCategory : activeSubCategoryListBasedOnCategoryId) {
            responseDTOList.add(SubCategoryMapper.toResponseDTO(subCategory));
        }

        // Step 5: Return the list of response DTOs
        return responseDTOList;
    }


        @Override
        public SubCategoryResponseDTO updateSubCategory(Long subCategoryId, SubCategoryRequestDTO requestDTO) {
            // Step 1: Find the existing subcategory
            SubCategory existingSubCategory = subCategoryRepository.findById(subCategoryId)
                    .orElseThrow(() -> new SubCategoryNotFoundException(
                            "No SubCategory found with ID: " + subCategoryId + " for updating."));

            // Step 2: Update values
            existingSubCategory.setSubCategoryName(requestDTO.getSubCategoryName());
            existingSubCategory.setSubCategoryIconUrl(requestDTO.getSubCategoryIconUrl());


            // Step 3: Optionally update category if required
            if (!existingSubCategory.getCategory().getCategoryId().equals(requestDTO.getCategoryId())) {
                Category category = categoryRepository.findById(requestDTO.getCategoryId())
                        .orElseThrow(() -> new CategoryNotFoundException(
                                "Category ID " + requestDTO.getCategoryId() + " is invalid. Cannot update SubCategory."));
                existingSubCategory.setCategory(category);
            }

            // Step 4: Save and return response
            SubCategory updatedSubCategory = subCategoryRepository.save(existingSubCategory);
            return SubCategoryMapper.toResponseDTO(updatedSubCategory);
        }

    @Override
    public void deactivateSubCategory(Long subCategoryId) {
        // Step 1: Find the subcategory
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new SubCategoryNotFoundException(
                        "Cannot deactivate. SubCategory with ID " + subCategoryId + " not found."));

        // Step 2: Check if already inactive
        if (Boolean.FALSE.equals(subCategory.getSubCategoryActive())) {
            throw new SubCategoryStatusException(
                    "SubCategory with ID " + subCategoryId + " is already inactive.");
        }

        // Step 3: Mark as inactive
        subCategory.setSubCategoryActive(false);
        subCategoryRepository.save(subCategory);
    }


    @Override
    public void activateSubCategory(Long subCategoryId) {
        // Step 1: Find the subcategory
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new SubCategoryNotFoundException(
                        "Cannot activate. SubCategory with ID " + subCategoryId + " not found."));

        // Step 2: Check if already active
        if (Boolean.TRUE.equals(subCategory.getSubCategoryActive())) {
            throw new SubCategoryStatusException(
                    "SubCategory with ID " + subCategoryId + " is already active.");
        }

        // Step 3: Mark as active
        subCategory.setSubCategoryActive(true);
        subCategoryRepository.save(subCategory);
    }

    @Override
    public List<SubCategoryResponseDTO> searchSubCategories(String keyword) {
        List<SubCategory> subCategories = subCategoryRepository.findBySubCategoryNameContainingIgnoreCase(keyword);

        if (subCategories.isEmpty()) {
            throw new SubCategoryNotFoundException("No subcategories found matching: " + keyword);
        }

        List<SubCategoryResponseDTO> responseList = new ArrayList<>();
        for (SubCategory subCategory : subCategories) {
            responseList.add(SubCategoryMapper.toResponseDTO(subCategory));
        }

        return responseList;
    }


}
