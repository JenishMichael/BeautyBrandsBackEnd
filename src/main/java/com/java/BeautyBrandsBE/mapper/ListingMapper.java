

package com.java.BeautyBrandsBE.mapper;

import com.java.BeautyBrandsBE.dto.CategoryResponseDTO;
import com.java.BeautyBrandsBE.dto.SubCategoryResponseDTO;
import com.java.BeautyBrandsBE.dto.ListingRequestDTO;
import com.java.BeautyBrandsBE.dto.ListingResponseDTO;
import com.java.BeautyBrandsBE.model.Category;
import com.java.BeautyBrandsBE.model.Listing;
import com.java.BeautyBrandsBE.model.SubCategory;

import java.util.Set;
import java.util.stream.Collectors;

public class ListingMapper {

    public static Listing toEntity(ListingRequestDTO dto, Set<Category> categories, Set<SubCategory> subCategories) {
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
        listing.setCategories(categories);
        listing.setSubCategories(subCategories);
        return listing;
    }

    public static ListingResponseDTO toResponseDTO(Listing listing) {
        Set<CategoryResponseDTO> categoryDTOs = listing.getCategories().stream()
                .map(cat -> new CategoryResponseDTO(
                        cat.getCategoryId(),
                        cat.getCategoryName(),
                        cat.getCategoryIconUrl(),
                        cat.getCategoryActive()
                ))
                .collect(Collectors.toSet());

        Set<SubCategoryResponseDTO> subCategoryDTOs = listing.getSubCategories().stream()
                .map(sub -> new SubCategoryResponseDTO(
                        sub.getSubCategoryId(),
                        sub.getSubCategoryName(),
                        sub.getSubCategoryIconUrl(),
                        sub.getSubCategoryActive(),
                        sub.getCategory().getCategoryId()
                ))
                .collect(Collectors.toSet());

        return new ListingResponseDTO(
                listing.getListingId(),
                listing.getListingTitle(),
                listing.getDescription(),
                listing.getAddress(),
                listing.getCity(),
                listing.getContactNumber(),
                listing.getWhatsappNumber(),
                listing.getEmail(),
                listing.getImageUrl(),
                listing.getWebsite(),
                listing.getListingActive(),
                categoryDTOs,
                subCategoryDTOs
        );
    }
}
