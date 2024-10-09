package com.manica.productscatalogue.inventory.features;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface VariantFeatureRepository extends JpaRepository<VariantFeature, Long> {

    Set<VariantFeature> findByVariant_VariantId(Long variantId);

}