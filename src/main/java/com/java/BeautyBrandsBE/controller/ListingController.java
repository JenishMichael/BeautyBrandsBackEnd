package com.java.BeautyBrandsBE.controller;

import com.java.BeautyBrandsBE.dto.ListingRequestDTO;
import com.java.BeautyBrandsBE.dto.ListingResponseDTO;
import com.java.BeautyBrandsBE.service.ListingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ListingController {

    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @PostMapping("/api/v1/listings/save")
    public ResponseEntity<ListingResponseDTO> createListing(@Valid @RequestBody ListingRequestDTO listingRequestDTO) {
        ListingResponseDTO createdListing = listingService.createListing(listingRequestDTO);
        return new ResponseEntity<>(createdListing, HttpStatus.CREATED);
    }


    @GetMapping("/api/listings/allActive")
    public ResponseEntity<List<ListingResponseDTO>> getAllActiveListings() {
        List<ListingResponseDTO> listings = listingService.getAllActiveListings();
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }


    @GetMapping("/api/listings/active/{id}")
    public ResponseEntity<ListingResponseDTO> getListingByIdIfActive(@PathVariable Long id) {
        ListingResponseDTO listing = listingService.getListingByIdIfActive(id);
        return new ResponseEntity<>(listing, HttpStatus.OK);
    }

    @PutMapping("/api/listings/update/{id}")
    public ResponseEntity<ListingResponseDTO> updateListing(@PathVariable Long id, @RequestBody @Valid ListingRequestDTO dto) {
        ListingResponseDTO response = listingService.updateListing(id, dto);
        return ResponseEntity.ok(response);
    }

    // To Soft Delete Listing
    @PutMapping("/api/listings/delete/{id}")
    public ResponseEntity<String> softDeleteListing(@PathVariable Long id) {
        listingService.softDeleteListing(id);
        return ResponseEntity.ok("Listing with ID " + id + " has been deactivated.");
    }

    // To Reactivate Listing
    @PutMapping("/api/listings/activate/{id}")
    public ResponseEntity<String> reactivateListing(@PathVariable Long id) {
        listingService.reactivateListing(id);
        return ResponseEntity.ok("Listing with ID " + id + " has been reactivated.");
    }

    @GetMapping("/api/listings/subcategory/{subCategoryId}")
    public ResponseEntity<List<ListingResponseDTO>> getListingsBySubCategoryId(@PathVariable Long subCategoryId) {
        List<ListingResponseDTO> listings = listingService.getActiveListingsBySubCategoryId(subCategoryId);
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/api/listings/category/{categoryId}")
    public ResponseEntity<List<ListingResponseDTO>> getListingsByCategoryId(@PathVariable Long categoryId) {
        List<ListingResponseDTO> listings = listingService.getActiveListingsByCategoryId(categoryId);
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/api/listings/search")
    public ResponseEntity<List<ListingResponseDTO>> searchListings(@RequestParam String keyword) {
        List<ListingResponseDTO> results = listingService.searchListings(keyword);
        return ResponseEntity.ok(results);
    }



}
