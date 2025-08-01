package com.java.BeautyBrandsBE.service;

import com.java.BeautyBrandsBE.dto.ListingRequestDTO;
import com.java.BeautyBrandsBE.dto.ListingResponseDTO;

import java.util.List;

public interface ListingService {

    // Create a new listing
    ListingResponseDTO createListing(ListingRequestDTO dto);

    // Get all active listings
    List<ListingResponseDTO> getAllActiveListings();

    // Get a listing by its ID (only if active)
    ListingResponseDTO getListingByIdIfActive(Long id);

    // Update a listing
    ListingResponseDTO updateListing(Long id, ListingRequestDTO dto);

    // Soft delete a listing (mark as inactive)
    void softDeleteListing(Long id);

    // listing (mark as active)
    void reactivateListing(Long id);

    // Get listings by subcategory (optional, only if required)
    List<ListingResponseDTO> getActiveListingsBySubCategoryId(Long subCategoryId);

    // Get listings by category
    List<ListingResponseDTO> getActiveListingsByCategoryId(Long categoryId); // Optional

    //Search Bar
    List<ListingResponseDTO> searchListings(String keyword);

}
