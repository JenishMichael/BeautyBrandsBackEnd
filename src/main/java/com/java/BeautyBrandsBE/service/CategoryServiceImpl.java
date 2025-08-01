package com.java.BeautyBrandsBE.service;

import com.java.BeautyBrandsBE.dto.CategoryRequestDTO;
import com.java.BeautyBrandsBE.dto.CategoryResponseDTO;
import com.java.BeautyBrandsBE.exception.CategoryAlreadyPresentException;
import com.java.BeautyBrandsBE.exception.CategoryNotFoundException;
import com.java.BeautyBrandsBE.exception.CategoryStateException;
import com.java.BeautyBrandsBE.mapper.CategoryMapper;
import com.java.BeautyBrandsBE.model.Category;
import com.java.BeautyBrandsBE.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        //1. Checking is Category is Already with same as of categoryRequestDto
        Optional<Category> existingCategory = categoryRepository.findByCategoryName(categoryRequestDTO.getCategoryName());
        if(existingCategory.isPresent()){
            throw new CategoryAlreadyPresentException("Another category with the same name already exists "+ categoryRequestDTO.getCategoryName());
        }

        //2. Converting categoryRequestDTO to Entity Category and saving it in DB
        Category category = categoryRepository.save(CategoryMapper.toEntity(categoryRequestDTO));

        //3. Converting Entity Category to categoryResponseDto and returning it
        return CategoryMapper.toDto(category);
    }

    @Transactional
    @Override
    public CategoryResponseDTO updateCategory(Long categoryId, CategoryRequestDTO updatingCategoryRequestDto) {
        //1. Checking Is Category with the same categoryId is present in DB
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));

        //2. Checking Is Category with the same categoryName is present in DB
        Optional<Category> duplicateCategory = categoryRepository.findByCategoryName(updatingCategoryRequestDto.getCategoryName());

        //3. Checking Is Category with the same categoryName And its DB's CategoryId and updatingCategory is not same
        if(duplicateCategory.isPresent() && !duplicateCategory.get().getCategoryId().equals(categoryId)){
            throw new CategoryAlreadyPresentException("Another category with the same name already exists "+ updatingCategoryRequestDto.getCategoryName());
        }

        existingCategory.setCategoryName(updatingCategoryRequestDto.getCategoryName());
        existingCategory.setCategoryIconUrl(updatingCategoryRequestDto.getCategoryIconUrl());
        existingCategory.setCategoryActive(updatingCategoryRequestDto.getCategoryActive());

        Category savedCategory =  categoryRepository.save(existingCategory);
        return CategoryMapper.toDto(savedCategory);

    }


    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        //1. Fetch all categories from the database
        List<Category> listOfCategories = categoryRepository.findAll();

        //2. If no categories are found, throw a custom exception
        if (listOfCategories.isEmpty()) {
            throw new CategoryNotFoundException("No Category is Present in the List, Please add a new Category");
        }

        //3. Initialize an empty list to hold the response DTOs
        List<CategoryResponseDTO> listOfCategoryResponseDTO = new ArrayList<>();

        //4. Temporary DTO object to store the converted value
        CategoryResponseDTO dtoResponse = null;

        //5. Iterate over each Category entity and convert it to DTO
        for (Category c : listOfCategories) {
            //6. Convert entity to DTO using the mapper
            dtoResponse = CategoryMapper.toDto(c);

            //7. Add the DTO to the response list
            listOfCategoryResponseDTO.add(dtoResponse);
        }

        //8. Return the final list of DTOs
        return listOfCategoryResponseDTO;
    }


    @Override
    public List<CategoryResponseDTO> getActiveCategories() {
        //1. Fetch all categories where categoryActive == true
        List<Category> listOfActiveCategory = categoryRepository.findByCategoryActiveTrue();

        //2. If no active categories found, throw custom exception
        if (listOfActiveCategory.isEmpty()) {
            throw new CategoryNotFoundException("No active categories Found.");
        }

        //3. Create an empty list to store converted DTOs
        List<CategoryResponseDTO> listOfActiveCategoryResponseDTO = new ArrayList<>();

        //4. Iterate through the active categories and convert each to DTO
        CategoryResponseDTO dtoResponse = null;
        for (Category c : listOfActiveCategory) {
            //5. Convert entity to DTO using mapper and add to the list
            listOfActiveCategoryResponseDTO.add(CategoryMapper.toDto(c));
        }

        //6. Return the list of active category DTOs
        return listOfActiveCategoryResponseDTO;
    }

    @Override
    public List<CategoryResponseDTO> getInactiveCategories() {
        //1. Fetch all categories where 'categoryActive' is false
        List<Category> listOfInactiveCategory = categoryRepository.findByCategoryActiveFalse();

        //2. If no inactive categories are found, throw a custom exception
        if (listOfInactiveCategory.isEmpty()) {
            throw new CategoryNotFoundException("No inactive categories found.");
        }

        //3. Initialize an empty list to hold the response DTOs
        List<CategoryResponseDTO> listOfInactiveCategoryResponseDTO = new ArrayList<>();

        //4. Temporary DTO object to store the converted value
        CategoryResponseDTO dtoResponse = null;

        //5. Iterate over each Category entity and convert it to DTO
        for (Category c : listOfInactiveCategory) {
            // Convert entity to DTO using the mapper
            dtoResponse = CategoryMapper.toDto(c);

            // Add the DTO to the response list
            listOfInactiveCategoryResponseDTO.add(dtoResponse);
        }

        //6. Return the final list of inactive category DTOs
        return listOfInactiveCategoryResponseDTO;
    }


    @Override
    public Long countAllCategories() {
        long count = categoryRepository.count();

        if (count < 1) {
            throw new CategoryNotFoundException("No Category Found");
        }

        return count;
    }


    @Override
    public Long countActiveCategories() {
        long count = categoryRepository.countByCategoryActiveTrue();
        if (count < 1) {
            throw new CategoryNotFoundException("No active categories found");
        }
        return count;
    }

    @Override
    public Long countInactiveCategories() {
        long count = categoryRepository.countByCategoryActiveFalse();
        if (count < 1) {
            throw new CategoryNotFoundException("No inactive categories found");
        }
        return count;
    }

    @Transactional
    @Override
    public CategoryResponseDTO activateCategory(long id) {
        Category categoryTobeActivated = categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException("Category not found with ID: " + id));

        if(categoryTobeActivated.getCategoryActive()){
            throw new CategoryStateException("Category Is Already Active");
        }

        categoryTobeActivated.setCategoryActive(true);

        Category updatedCategory = categoryRepository.save(categoryTobeActivated);


        return CategoryMapper.toDto(updatedCategory);
    }

    @Transactional
    @Override
    public CategoryResponseDTO deactivateCategory(long id) {
        Category categoryTobeDeactivated = categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException("Category not found with ID: " + id));

        if(!categoryTobeDeactivated.getCategoryActive()){
            throw new CategoryStateException("Category Is Already Inactive");
        }

        categoryTobeDeactivated.setCategoryActive(false);

        Category updatedCategory = categoryRepository.save(categoryTobeDeactivated);


        return CategoryMapper.toDto(updatedCategory);
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException("Category not found with ID: " + id));

        return CategoryMapper.toDto(category);
    }




}
