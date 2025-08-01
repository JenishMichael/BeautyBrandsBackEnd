package com.java.BeautyBrandsBE.service;

import com.java.BeautyBrandsBE.dto.ListingRequestDTO;
import com.java.BeautyBrandsBE.dto.ListingResponseDTO;
import com.java.BeautyBrandsBE.exception.*;
import com.java.BeautyBrandsBE.mapper.ListingMapper;
import com.java.BeautyBrandsBE.model.Category;
import com.java.BeautyBrandsBE.model.Listing;
import com.java.BeautyBrandsBE.model.SubCategory;
import com.java.BeautyBrandsBE.repository.CategoryRepository;
import com.java.BeautyBrandsBE.repository.ListingRepository;
import com.java.BeautyBrandsBE.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListingServiceImpl implements ListingService{

    @Autowired
    private ListingRepository listingRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Override
    public ListingResponseDTO createListing(ListingRequestDTO dto) {
        // 1. Validate Category
        Category existingCategory = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category ID " + dto.getCategoryId() + " is not valid"));

        // 2. Validate SubCategory
        SubCategory existingSubCategory = subCategoryRepository.findById(dto.getSubCategoryId())
                .orElseThrow(() -> new SubCategoryNotFoundException("SubCategory ID " + dto.getSubCategoryId() + " is not valid"));

        // 3. Ensure SubCategory belongs to Category
        if (!existingSubCategory.getCategory().getCategoryId().equals(dto.getCategoryId())) {
            throw new SubCategoryMismatchException("SubCategory ID " + dto.getSubCategoryId()
                    + " does not belong to Category ID " + dto.getCategoryId());
        }

        // 4. Save Listing
        Listing savedListing = listingRepository.save(
                ListingMapper.toEntity(dto, existingCategory, existingSubCategory)
        );

        // 5. Return DTO
        return ListingMapper.toResponseDTO(savedListing);
    }


    @Override
    public List<ListingResponseDTO> getAllActiveListings() {
        List<Listing> listings= listingRepository.findAllByListingActiveTrue();

        if(listings.isEmpty()){
            throw new ListingNotFoundException("No Active Listing Present");
        }

        return  listings.stream().map(ListingMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public ListingResponseDTO getListingByIdIfActive(Long id) {

        Listing listing = listingRepository.findByListingIdAndListingActiveTrue(id)
                .orElseThrow(()->  new ListingNotFoundException("Listing not found with ID: " + id + ". It might be inactive or deleted."));

        return ListingMapper.toResponseDTO(listing);
    }

    @Override
    public ListingResponseDTO updateListing(Long id, ListingRequestDTO dto) {
        // 1. Fetch existing listing
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ListingNotFoundException("Listing not found with ID: " + id));

        if (!listing.getListingActive()) {
            throw new ListingNotFoundException("Listing with ID: " + id + " is inactive or deleted");
        }

        // 2. Fetch category and subcategory
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + dto.getCategoryId()));

        SubCategory subCategory = subCategoryRepository.findById(dto.getSubCategoryId())
                .orElseThrow(() -> new SubCategoryNotFoundException("SubCategory not found with ID: " + dto.getSubCategoryId()));

        //
        if(!subCategory.getCategory().getCategoryId().equals(dto.getCategoryId())){
            throw new SubCategoryMismatchException("SubCategory does not belong to the specified Category");
        }

        // 3. Update listing fields
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
        listing.setCategory(category);
        listing.setSubCategory(subCategory);

        // 4. Save updated listing
        Listing updatedListing = listingRepository.save(listing);

        // 5. Convert to response DTO
        return ListingMapper.toResponseDTO(updatedListing);
    }


    @Override
    public void softDeleteListing(Long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ListingNotFoundException("Listing with id " + id + " not found"));

        if (!listing.getListingActive()) {
            throw new ListingStateException("Listing is already inactive");
        }

        listing.setListingActive(false);
        listingRepository.save(listing);
    }

    @Override
    public void reactivateListing(Long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ListingNotFoundException("Listing with id " + id + " not found"));

        if (listing.getListingActive()) {
            throw new ListingStateException("Listing is already active");
        }

        listing.setListingActive(true);
        listingRepository.save(listing);
    }


    @Override
    public List<ListingResponseDTO> getActiveListingsBySubCategoryId(Long subCategoryId) {
        // Check if subcategory exists
        SubCategory existingSubCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new SubCategoryNotFoundException("Subcategory not found with ID: " + subCategoryId));

        // Fetch listings by subcategoryId where listing is active
        List<Listing> listings = listingRepository.findBySubCategory_SubCategoryIdAndListingActiveTrue(subCategoryId);

        if (listings.isEmpty()) {
            throw new ListingNotFoundException("No active listings found for SubCategory ID: " + subCategoryId);
        }

        return listings.stream().map(ListingMapper::toResponseDTO).collect(Collectors.toList());


    }

    @Override
    public List<ListingResponseDTO> getActiveListingsByCategoryId(Long categoryId) {
        // Check if category exists
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));

        // Get active listings via nested query
        List<Listing> listings = listingRepository.findByCategory_CategoryIdAndListingActiveTrue(categoryId);

        if (listings.isEmpty()) {
            throw new ListingNotFoundException("No active listings found for Category ID: " + categoryId);
        }

        return listings.stream().map(ListingMapper::toResponseDTO).collect(Collectors.toList());
    }


    @Override
    public List<ListingResponseDTO> searchListings(String keyword) {
        List<Listing> listings = listingRepository.searchActiveListingsByKeyword(keyword);

        if (listings.isEmpty()) {
            throw new ListingNotFoundException("No listings found for keyword: " + keyword);
        }

        return listings.stream()
                .map(ListingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

}
