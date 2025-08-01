package com.java.BeautyBrandsBE.controller;

import com.java.BeautyBrandsBE.dto.SubCategoryRequestDTO;
import com.java.BeautyBrandsBE.dto.SubCategoryResponseDTO;
import com.java.BeautyBrandsBE.service.SubCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @PostMapping("/api/v1/createSubCategory")
    public ResponseEntity<SubCategoryResponseDTO> createSubCategory(@Valid @RequestBody SubCategoryRequestDTO subCategoryRequestDTO){
        return new ResponseEntity<>(subCategoryService.createSubCategory(subCategoryRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/subCategories")
    public ResponseEntity<List<SubCategoryResponseDTO>> getAllSubCategories() {
        List<SubCategoryResponseDTO> subCategories = subCategoryService.getAllSubCategories();
        return ResponseEntity.ok(subCategories);
    }

    @GetMapping("/api/v1/subCategories/active")
    public ResponseEntity<List<SubCategoryResponseDTO>> getActiveSubCategories() {
        List<SubCategoryResponseDTO> activeSubCategories = subCategoryService.getActiveSubCategories();
        return ResponseEntity.ok(activeSubCategories);
    }


    @GetMapping("/api/v1/subCategory/{subCategoryId}")
    public ResponseEntity<SubCategoryResponseDTO> getSubCategoryById(@PathVariable Long subCategoryId) {
        SubCategoryResponseDTO dto = subCategoryService.getSubCategoryById(subCategoryId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/api/v1/subCategory/category/{categoryId}")
    public ResponseEntity<List<SubCategoryResponseDTO>> getSubCategoriesByCategoryId(@PathVariable Long categoryId) {
        List<SubCategoryResponseDTO> subCategories = subCategoryService.getSubCategoriesByCategoryId(categoryId);
        return ResponseEntity.ok(subCategories);
    }


    // GET: Fetch all active subcategories by Category ID
    @GetMapping("/api/v1/subCategory/active/by-category/{categoryId}")
    public ResponseEntity<List<SubCategoryResponseDTO>> getActiveSubCategoriesByCategoryId(@PathVariable Long categoryId) {
        List<SubCategoryResponseDTO> activeSubCategories = subCategoryService.getActiveSubCategoriesByCategoryId(categoryId);
        return ResponseEntity.ok(activeSubCategories);
    }


    @PutMapping("/api/v1/subCategory/{subCategoryId}")
    public ResponseEntity<SubCategoryResponseDTO> updateSubCategory(
            @PathVariable Long subCategoryId,
            @Valid @RequestBody SubCategoryRequestDTO subCategoryRequestDTO) {

        SubCategoryResponseDTO updatedSubCategory = subCategoryService.updateSubCategory(subCategoryId, subCategoryRequestDTO);
        return ResponseEntity.ok(updatedSubCategory);
    }


    @PatchMapping("/api/v1/subCategory/{subCategoryId}/deactivate")
    public ResponseEntity<String> deactivateSubCategory(@PathVariable Long subCategoryId) {
        subCategoryService.deactivateSubCategory(subCategoryId);
        return ResponseEntity.ok("SubCategory with ID " + subCategoryId + " has been deactivated successfully.");
    }

    @PatchMapping("/api/v1/subCategory/{subCategoryId}/activate")
    public ResponseEntity<String> activateSubCategory(@PathVariable Long subCategoryId) {
        subCategoryService.activateSubCategory(subCategoryId);
        return ResponseEntity.ok("SubCategory with ID " + subCategoryId + " has been activated successfully.");
    }


    @GetMapping("/api/v1/subCategory/search")
    public ResponseEntity<List<SubCategoryResponseDTO>> searchSubCategories(
            @RequestParam("keyword") String keyword) {
        List<SubCategoryResponseDTO> result = subCategoryService.searchSubCategories(keyword);
        return ResponseEntity.ok(result);
    }



}
