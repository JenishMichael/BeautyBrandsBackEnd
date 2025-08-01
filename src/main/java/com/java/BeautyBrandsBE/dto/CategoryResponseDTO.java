package com.java.BeautyBrandsBE.dto;

public class CategoryResponseDTO {

//    private Long categoryId;
//    private String categoryName;
//    private String categoryIconUrl;
//    private Boolean categoryActive;

    private Long categoryId;
    private String categoryName;
    private String categoryIconUrl;
    private Boolean categoryActive;

    public CategoryResponseDTO() {
    }

    public CategoryResponseDTO(Long categoryId, String categoryName, String categoryIconUrl, Boolean categoryActive) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryIconUrl = categoryIconUrl;
        this.categoryActive = categoryActive;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
        return "CategoryResponseDTO{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryIconUrl='" + categoryIconUrl + '\'' +
                ", categoryActive=" + categoryActive +
                '}';
    }
}
