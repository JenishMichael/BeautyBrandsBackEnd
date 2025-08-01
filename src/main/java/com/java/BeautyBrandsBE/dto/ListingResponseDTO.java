package com.java.BeautyBrandsBE.dto;

import java.util.Set;

public class ListingResponseDTO {
    private Long listingId;
    private String listingTitle;
    private String description;
    private String address;
    private String city;
    private String contactNumber;
    private String whatsappNumber;
    private String email;
    private String imageUrl;
    private String website;
    private Boolean listingActive;

    private Set<CategoryResponseDTO> categories;
    private Set<SubCategoryResponseDTO> subCategories;

    public ListingResponseDTO() {
    }

    public ListingResponseDTO(Long listingId, String listingTitle, String description, String address, String city, String contactNumber, String whatsappNumber, String email, String imageUrl, String website, Boolean listingActive, Set<CategoryResponseDTO> categories, Set<SubCategoryResponseDTO> subCategories) {
        this.listingId = listingId;
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
        this.categories = categories;
        this.subCategories = subCategories;
    }

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

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

    public Set<CategoryResponseDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryResponseDTO> categories) {
        this.categories = categories;
    }

    public Set<SubCategoryResponseDTO> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<SubCategoryResponseDTO> subCategories) {
        this.subCategories = subCategories;
    }

    @Override
    public String toString() {
        return "ListingResponseDTO{" +
                "listingId=" + listingId +
                ", listingTitle='" + listingTitle + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", whatsappNumber='" + whatsappNumber + '\'' +
                ", email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", website='" + website + '\'' +
                ", listingActive=" + listingActive +
                ", categories=" + categories +
                ", subCategories=" + subCategories +
                '}';
    }
}
