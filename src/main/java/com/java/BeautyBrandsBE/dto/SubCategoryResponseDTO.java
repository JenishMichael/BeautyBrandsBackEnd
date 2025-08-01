package com.java.BeautyBrandsBE.dto;

public class SubCategoryResponseDTO {

//    private Long subCategoryId;
//    private String subCategoryName;
//    private String subCategoryIconUrl;
//    private Boolean subCategoryActive = true;
//    private Long categoryId;

    private Long subCategoryId;
    private String subCategoryName;
    private String subCategoryIconUrl;
    private Boolean subCategoryActive = true;
    private Long categoryId;

    public SubCategoryResponseDTO() {
    }

    public SubCategoryResponseDTO(Long subCategoryId, String subCategoryName, String subCategoryIconUrl, Boolean subCategoryActive, Long categoryId) {
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.subCategoryIconUrl = subCategoryIconUrl;
        this.subCategoryActive = subCategoryActive;
        this.categoryId = categoryId;
    }

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
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
        return "SubCategoryResponseDTO{" +
                "subCategoryId=" + subCategoryId +
                ", subCategoryName='" + subCategoryName + '\'' +
                ", subCategoryIconUrl='" + subCategoryIconUrl + '\'' +
                ", subCategoryActive=" + subCategoryActive +
                ", categoryId=" + categoryId +
                '}';
    }
}
