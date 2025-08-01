package com.java.BeautyBrandsBE.dto;

import jakarta.validation.constraints.*;

public class ListingRequestDTO {

//    private String listingTitle;
//    private String description;
//    private String address;
//    private String city;
//    private String contactNumber;
//    private String whatsappNumber;
//    private String email;
//    private String imageUrl;
//    private String website;
//    private Boolean listingActive;
//
//    private Long categoryId;
//    private Long subCategoryId;

    @NotBlank(message = "Listing title is required")
    @Size(max = 100, message = "Listing title must be at most 100 characters")
    private String listingTitle;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be 10 digits")
    private String contactNumber;

    @NotBlank(message = "WhatsApp number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "WhatsApp number must be 10 digits")
    private String whatsappNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

//    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    private String website; // Optional, no validation

//    @NotNull(message = "Listing active status is required")
    private Boolean listingActive = true;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "SubCategory ID is required")
    private Long subCategoryId;

    public ListingRequestDTO() {
    }

    public ListingRequestDTO(String listingTitle, String description, String address, String city,
                             String contactNumber, String whatsappNumber, String email, String imageUrl,
                             String website, Boolean listingActive, Long categoryId, Long subCategoryId) {
        this.listingTitle = listingTitle;
        this.description = description;
        this.address = address;
        this.city = city;
        this.contactNumber = contactNumber;
        this.whatsappNumber = whatsappNumber;
        this.email = email;
        this.imageUrl = imageUrl;
        this.website = website;
        this.listingActive = listingActive;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
    }

    // Getters and Setters

    public String getListingTitle() {
        return listingTitle;
    }

    public void setListingTitle(String listingTitle) {
        this.listingTitle = listingTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getWhatsappNumber() {
        return whatsappNumber;
    }

    public void setWhatsappNumber(String whatsappNumber) {
        this.whatsappNumber = whatsappNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Boolean getListingActive() {
        return listingActive;
    }

    public void setListingActive(Boolean listingActive) {
        this.listingActive = listingActive;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }
}
