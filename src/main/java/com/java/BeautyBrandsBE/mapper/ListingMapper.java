package com.java.BeautyBrandsBE.mapper;

import com.java.BeautyBrandsBE.dto.ListingRequestDTO;
import com.java.BeautyBrandsBE.dto.ListingResponseDTO;
import com.java.BeautyBrandsBE.model.Category;
import com.java.BeautyBrandsBE.model.Listing;
import com.java.BeautyBrandsBE.model.SubCategory;

public class ListingMapper {

    // Convert RequestDTO to Entity
    public static Listing toEntity(ListingRequestDTO dto, Category category, SubCategory subCategory) {
        Listing listing = new Listing();
        listing.setListingTitle(dto.getListingTitle());
        listing.setDescription(dto.getDescription());
        listing.setAddress(dto.getAddress());
        listing.setCity(dto.getCity());
        listing.setContactNumber(dto.getContactNumber());
        listing.setWhatsappNumber(dto.getWhatsappNumber());
        listing.setEmail(dto.getEmail());
        listing.setImageUrl(dto.getImageUrl());
        listing.setWebsite(dto.getWebsite());
        listing.setListingActive(dto.getListingActive());

        // Set Category and SubCategory objects
        listing.setCategory(category);
        listing.setSubCategory(subCategory);

        return listing;
    }

    // Convert Entity to ResponseDTO
    public static ListingResponseDTO toResponseDTO(Listing listing) {
        ListingResponseDTO dto = new ListingResponseDTO();
        dto.setListingId(listing.getListingId());
        dto.setListingTitle(listing.getListingTitle());
        dto.setDescription(listing.getDescription());
        dto.setAddress(listing.getAddress());
        dto.setCity(listing.getCity());
        dto.setContactNumber(listing.getContactNumber());
        dto.setWhatsappNumber(listing.getWhatsappNumber());
        dto.setEmail(listing.getEmail());
        dto.setImageUrl(listing.getImageUrl());
        dto.setWebsite(listing.getWebsite());
        dto.setListingActive(listing.getListingActive());

        // Set categoryId and subCategoryId if present
        if (listing.getCategory() != null) {
            dto.setCategoryId(listing.getCategory().getCategoryId());
        }
        if (listing.getSubCategory() != null) {
            dto.setSubCategoryId(listing.getSubCategory().getSubCategoryId());
        }

        return dto;
    }
}
