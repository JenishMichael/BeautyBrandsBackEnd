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
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ListingServiceImpl implements ListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private EmailService emailService;

    private static final Logger log = LoggerFactory.getLogger(ListingServiceImpl.class);

    @Transactional
    @Override
    public ListingResponseDTO createListing(ListingRequestDTO dto) {
        // 1. Validate and fetch categories
        List<Long> requestedCategoryIds = new ArrayList<>(dto.getCategoryIds());
        List<Category> foundCategories = categoryRepository.findAllById(requestedCategoryIds);

        Set<Long> foundCategoryIds = foundCategories.stream()
                .map(Category::getCategoryId)
                .collect(Collectors.toSet());

        List<Long> missingCategoryIds = requestedCategoryIds.stream()
                .filter(id -> !foundCategoryIds.contains(id))
                .collect(Collectors.toList());

        if (!missingCategoryIds.isEmpty()) {
            throw new CategoryNotFoundException("Missing Category IDs: " + missingCategoryIds);
        }

        Set<Category> categories = new HashSet<>(foundCategories);

        // 2. Validate and fetch subcategories
        List<Long> requestedSubCategoryIds = new ArrayList<>(dto.getSubCategoryIds());
        List<SubCategory> foundSubCategories = subCategoryRepository.findAllById(requestedSubCategoryIds);

        Set<Long> foundSubCategoryIds = foundSubCategories.stream()
                .map(SubCategory::getSubCategoryId)
                .collect(Collectors.toSet());

        List<Long> missingSubCategoryIds = requestedSubCategoryIds.stream()
                .filter(id -> !foundSubCategoryIds.contains(id))
                .collect(Collectors.toList());

        if (!missingSubCategoryIds.isEmpty()) {
            throw new SubCategoryNotFoundException("Missing SubCategory IDs: " + missingSubCategoryIds);
        }

        Set<SubCategory> subCategories = new HashSet<>(foundSubCategories);

        // 3. Check each subcategory belongs to a selected category
        for (SubCategory sub : subCategories) {
            Long parentCategoryId = sub.getCategory().getCategoryId();
            if (!dto.getCategoryIds().contains(parentCategoryId)) {
                throw new SubCategoryMismatchException(
                        "SubCategory ID " + sub.getSubCategoryId() +
                                " does not belong to any of the selected Category IDs " + dto.getCategoryIds()
                );
            }
        }

        // Step 1: Get the category IDs that each subcategory belongs to
        Set<Long> subCategoryParentIds = subCategories.stream()
                .map(sub -> sub.getCategory().getCategoryId())
                .collect(Collectors.toSet());

        // Step 2: Find categoryIds that are not linked to any selected subCategory
        List<Long> orphanedCategoryIds = dto.getCategoryIds().stream()
                .filter(catId -> !subCategoryParentIds.contains(catId))
                .collect(Collectors.toList());

        // Step 3: Throw exception if any selected category has no matching subCategory
        if (!orphanedCategoryIds.isEmpty()) {
            throw new SubCategoryMismatchException(
                    "These Category IDs have no selected subcategories: " + orphanedCategoryIds
            );
        }

        // 4. Save Listing
        Listing listing = ListingMapper.toEntity(dto, categories, subCategories);
        ListingResponseDTO saved = ListingMapper.toResponseDTO(listingRepository.save(listing));

        //5.Send Email
        try {
            emailService.sendEmailToAdmin(saved);
        } catch (Exception e) {
            log.error("Email sending failed for listing ID {}: {}", saved.getListingId(), e.getMessage());
        }
        return saved;
    }


    @Override
    public ListingResponseDTO getListingByIdIfActive(Long id) {
        Listing listing = listingRepository.findByListingIdAndListingActiveTrue(id)
                .orElseThrow(() -> new ListingNotFoundException(
                        "Listing not found with ID: " + id + ". It might be inactive or deleted."));

        return ListingMapper.toResponseDTO(listing);
    }

    @Override
    public List<ListingResponseDTO> getAllActiveListings() {
        List<Listing> listings = listingRepository.findAllByListingActiveTrue();

        if (listings.isEmpty()) {
            throw new ListingNotFoundException("No Active Listings Present");
        }

        return listings.stream().map(ListingMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public ListingResponseDTO updateListing(Long id, ListingRequestDTO dto) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ListingNotFoundException("Listing not found with ID: " + id));

        if (!listing.getListingActive()) {
            throw new ListingNotFoundException("Listing with ID: " + id + " is inactive or deleted");
        }

        // 1. Validate categories
        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(dto.getCategoryIds()));
        if (categories.size() != dto.getCategoryIds().size()) {
            throw new CategoryNotFoundException("One or more Category IDs are invalid: " + dto.getCategoryIds());
        }

        // 2. Validate subcategories
        Set<SubCategory> subCategories = new HashSet<>(subCategoryRepository.findAllById(dto.getSubCategoryIds()));
        if (subCategories.size() != dto.getSubCategoryIds().size()) {
            throw new SubCategoryNotFoundException("One or more SubCategory IDs are invalid: " + dto.getSubCategoryIds());
        }

        // 3. Validate subcategories belong to selected categories
        for (SubCategory subCategory : subCategories) {
            if (subCategory.getCategory() == null ||
                    !dto.getCategoryIds().contains(subCategory.getCategory().getCategoryId())) {
                throw new SubCategoryMismatchException(
                        "SubCategory ID " + subCategory.getSubCategoryId() +
                                " does not belong to the selected Category IDs: " + dto.getCategoryIds());
            }
        }

        // 4. Update fields
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

        Listing updated = listingRepository.save(listing);
        return ListingMapper.toResponseDTO(updated);
    }

    @Override
    public void softDeleteListing(Long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ListingNotFoundException("Listing with ID " + id + " not found"));

        if (!listing.getListingActive()) {
            throw new ListingStateException("Listing is already inactive");
        }

        listing.setListingActive(false);
        listingRepository.save(listing);
    }

    @Override
    public void reactivateListing(Long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ListingNotFoundException("Listing with ID " + id + " not found"));

        if (listing.getListingActive()) {
            throw new ListingStateException("Listing is already active");
        }

        listing.setListingActive(true);
        listingRepository.save(listing);
    }

    @Override
    public List<ListingResponseDTO> getActiveListingsByCategoryId(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException("Category not found with ID: " + categoryId);
        }

        List<Listing> listings = listingRepository.findByCategories_CategoryIdAndListingActiveTrue(categoryId);

        if (listings.isEmpty()) {
            throw new ListingNotFoundException("No active listings found for Category ID: " + categoryId);
        }

        return listings.stream()
                .map(listing -> ListingMapper.toResponseDTO(listing))
                .collect(Collectors.toList());
    }

    @Override
    public List<ListingResponseDTO> getActiveListingsBySubCategoryId(Long subCategoryId) {
        if (!subCategoryRepository.existsById(subCategoryId)) {
            throw new SubCategoryNotFoundException("Subcategory not found with ID: " + subCategoryId);
        }

        List<Listing> listings = listingRepository.findBySubCategories_SubCategoryIdAndListingActiveTrue(subCategoryId);

        if (listings.isEmpty()) {
            throw new ListingNotFoundException("No active listings found for SubCategory ID: " + subCategoryId);
        }

        return listings.stream()
                .map(listing -> ListingMapper.toResponseDTO(listing))
                .collect(Collectors.toList());
    }

    @Override
    public List<ListingResponseDTO> searchListings(String keyword) {
        List<Listing> listings = listingRepository.searchActiveListingsByKeyword(keyword);

        if (listings.isEmpty()) {
            throw new ListingNotFoundException("No listings found for keyword: " + keyword);
        }

        return listings.stream()
                .map(listing -> ListingMapper.toResponseDTO(listing))
                .collect(Collectors.toList());
    }
}
