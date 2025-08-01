package com.java.BeautyBrandsBE.repository;

import com.java.BeautyBrandsBE.model.Category;
import com.java.BeautyBrandsBE.model.Listing;
import com.java.BeautyBrandsBE.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    // Get All active listings
    List<Listing> findAllByListingActiveTrue();

    // Get a listing by its ID (only if active)
    Optional<Listing> findByListingIdAndListingActiveTrue(Long id);

    // Get listings by category (many-to-many)
    List<Listing> findByCategories_CategoryIdAndListingActiveTrue(Long categoryId);

    // Get listings by subcategory (many-to-many)
    List<Listing> findBySubCategories_SubCategoryIdAndListingActiveTrue(Long subCategoryId);

    // Full-text search on listingTitle, description, city
    @Query("SELECT l FROM Listing l WHERE l.listingActive = true AND (" +
            "LOWER(l.listingTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.city) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Listing> searchActiveListingsByKeyword(@Param("keyword") String keyword);
}
