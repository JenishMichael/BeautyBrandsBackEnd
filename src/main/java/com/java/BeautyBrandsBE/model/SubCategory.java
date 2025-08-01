package com.java.BeautyBrandsBE.model;

import jakarta.persistence.*;


@Entity
@Table(name = "subcategories")
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subCategoryId;

    private String subCategoryName;

    private String subCategoryIconUrl;

    private Boolean subCategoryActive = true;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;


    public SubCategory() {
    }

    public SubCategory(Long subCategoryId, String subCategoryName, String subCategoryIconUrl, Boolean subCategoryActive, Category category) {
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.subCategoryIconUrl = subCategoryIconUrl;
        this.subCategoryActive = subCategoryActive;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "subCategoryId=" + subCategoryId +
                ", subCategoryName='" + subCategoryName + '\'' +
                ", subCategoryIconUrl='" + subCategoryIconUrl + '\'' +
                ", subCategoryActive=" + subCategoryActive +
                ", category=" + category +
                '}';
    }
}
