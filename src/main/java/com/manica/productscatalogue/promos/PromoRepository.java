package com.manica.productscatalogue.promos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PromoRepository extends JpaRepository<Promo, Long> {

    Optional<Promo> findByStartDateAndVariant_VariantId(LocalDate startDate, Long variantId);

    Optional<Promo> findByPromoCode(String promoCode);

    List<Promo> findByVariant_VariantId(Long variantId);

}