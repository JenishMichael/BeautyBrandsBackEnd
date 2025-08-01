package com.java.BeautyBrandsBE.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDTO {

    @NotBlank(message = "Category Name is required")
    private String categoryName;

//    @NotBlank(message = "Category Icon URL is required")
    private String categoryIconUrl;

    private Boolean categoryActive = true;

    public CategoryRequestDTO() {
    }

    public CategoryRequestDTO(String categoryName, String categoryIconUrl, Boolean categoryActive) {
        this.categoryName = categoryName;
        this.categoryIconUrl = categoryIconUrl;
        this.categoryActive = categoryActive;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryIconUrl() {
        return categoryIconUrl;
    }

    public void setCategoryIconUrl(String categoryIconUrl) {
        this.categoryIconUrl = categoryIconUrl;
    }

    public Boolean getCategoryActive() {
        return categoryActive;
    }

    public void setCategoryActive(Boolean categoryActive) {
        this.categoryActive = categoryActive;
    }

    @Override
    public String toString() {
        return "CategoryRequestDTO{" +
                "categoryName='" + categoryName + '\'' +
                ", categoryIconUrl='" + categoryIconUrl + '\'' +
                ", categoryActive=" + categoryActive +
                '}';
    }
}
