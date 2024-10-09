package com.manica.productscatalogue.inventory.rating;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findRatingByRatingId(Long ratingId);

    Optional<Rating> findByCreatedBy_EmailAndVariant_VariantId(String email, long variantId);


   List<Rating> findByVariant_VariantId(long variantId);


}