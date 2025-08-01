package com.java.BeautyBrandsBE.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SubCategoryRequestDTO {

//    private String subCategoryName;
//    private String subCategoryIconUrl;
//    private Boolean subCategoryActive = true;
//    private Long categoryId;bn

    @NotBlank(message = "SubCategory Name is required")
    private String subCategoryName;

    @NotBlank(message = "SubCategory Icon URL is required")
    private String subCategoryIconUrl;

    private Boolean subCategoryActive = true;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    public SubCategoryRequestDTO() {
    }

    public SubCategoryRequestDTO(String subCategoryName, String subCategoryIconUrl, Boolean subCategoryActive, Long categoryId) {
        this.subCategoryName = subCategoryName;
        this.subCategoryIconUrl = subCategoryIconUrl;
        this.subCategoryActive = subCategoryActive;
        this.categoryId = categoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getSubCategoryIconUrl() {
        return subCategoryIconUrl;
    }

    public void setSubCategoryIconUrl(String subCategoryIconUrl) {
        this.subCategoryIconUrl = subCategoryIconUrl;
    }

    public Boolean getSubCategoryActive() {
        return subCategoryActive;
    }

    public void setSubCategoryActive(Boolean subCategoryActive) {
        this.subCategoryActive = subCategoryActive;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "SubCategoryRequestDTO{" +
                "subCategoryName='" + subCategoryName + '\'' +
                ", subCategoryIconUrl='" + subCategoryIconUrl + '\'' +
                ", subCategoryActive=" + subCategoryActive +
                ", categoryId=" + categoryId +
                '}';
    }
}
