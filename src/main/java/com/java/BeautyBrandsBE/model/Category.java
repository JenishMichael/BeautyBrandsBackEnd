package com.java.BeautyBrandsBE.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank(message = "Category Name is Required")
    private String categoryName;

//    @NotBlank(message = "Category Profile Icon Path is Required")
    private String categoryIconUrl;

    private Boolean categoryActive = true;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<SubCategory> subCategories = new ArrayList<>();

    // Default constructor
    public Category() {
    }

    // Parameterized constructor
    public Category(Long categoryId, String categoryName, String categoryIconUrl, Boolean categoryActive, List<SubCategory> subCategories) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryIconUrl = categoryIconUrl;
        this.categoryActive = categoryActive;
        this.subCategories = subCategories;
    }

    // Getters and Setters
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

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryIconUrl='" + categoryIconUrl + '\'' +
                ", categoryActive=" + categoryActive +
                ", subCategories=" + subCategories +
                '}';
    }
}
