package com.java.BeautyBrandsBE.controller;

import com.java.BeautyBrandsBE.dto.CategoryRequestDTO;
import com.java.BeautyBrandsBE.dto.CategoryResponseDTO;
import com.java.BeautyBrandsBE.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/api/v1/createCategory")
    public ResponseEntity<CategoryResponseDTO>  createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO){
        return new ResponseEntity<>(categoryService.createCategory(categoryRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/api/v1/updateCategory/{categoryId}")
    public ResponseEntity<CategoryResponseDTO>  updateCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO, @PathVariable Long categoryId){
        return new ResponseEntity<>(categoryService.updateCategory(categoryId, categoryRequestDTO), HttpStatus.OK);
    }

    // Get all categories
    @GetMapping("/api/v2/allCategory")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategory(){
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    // Get all active categories
    @GetMapping("/api/v1/categories/active")
    public ResponseEntity<List<CategoryResponseDTO>> getActiveCategories() {
        List<CategoryResponseDTO> activeCategories = categoryService.getActiveCategories();
        return ResponseEntity.ok(activeCategories);
    }

    // Get all inactive categories
    @GetMapping("/api/v1/categories/inactive")
    public ResponseEntity<List<CategoryResponseDTO>> getInactiveCategories() {
        List<CategoryResponseDTO> inactiveCategories = categoryService.getInactiveCategories();
        return ResponseEntity.ok(inactiveCategories);
    }

    // Count all categories
    @GetMapping("/api/v1/count")
    public ResponseEntity<Long> countAllCategories() {
        return new ResponseEntity<>(categoryService.countAllCategories(), HttpStatus.OK);
    }

    // Count active categories
    @GetMapping("/api/v1/count/active")
    public ResponseEntity<Long> countActiveCategories() {
        return new ResponseEntity<>(categoryService.countActiveCategories(), HttpStatus.OK);
    }

    // Count inactive categories
    @GetMapping("/api/v1/count/inactive")
    public ResponseEntity<Long> countInactiveCategories() {
        return new ResponseEntity<>(categoryService.countInactiveCategories(), HttpStatus.OK);
    }

    //Activate Category
    @PutMapping("/api/v1/activate/{id}")
    public ResponseEntity<CategoryResponseDTO> activateCategory(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.activateCategory(id),HttpStatus.OK);
    }

    //Deactivate Category
    @PutMapping("/api/v1/deactivate/{id}")
    public ResponseEntity<CategoryResponseDTO> deactivateCategory(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.deactivateCategory(id),HttpStatus.OK);

    }

    @GetMapping("/api/v1/category/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        CategoryResponseDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

}
