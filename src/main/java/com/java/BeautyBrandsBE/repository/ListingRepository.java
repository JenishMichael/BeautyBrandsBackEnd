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
    // Find Category Obj By Listing Obj
    Optional<Category> findByCategoryCategoryId(Long categoryId);

    //Find SubCategory Obj By Listing Obj
    List<SubCategory> findBySubCategorySubCategoryId(Long subCategoryId);

    //Get All active in List
    List<Listing> findAllByListingActiveTrue();

    // Get a listing by its ID (only if active)  //getListingByIdIfActive
    Optional<Listing> findByListingIdAndListingActiveTrue(Long id);

    //Get List<Listings> based on subcategoryId
    List<Listing> findBySubCategory_SubCategoryIdAndListingActiveTrue(Long subCategoryId);

    //getActiveListingsByCategoryId
    List<Listing> findByCategory_CategoryIdAndListingActiveTrue(Long categoryId);


    @Query("SELECT l FROM Listing l WHERE l.listingActive = true AND (" +
            "LOWER(l.listingTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.city) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Listing> searchActiveListingsByKeyword(@Param("keyword") String keyword);

}
