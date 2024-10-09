package com.manica.productscatalogue.inventory;

import com.manica.productscatalogue.inventory.variant.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Set<Image> findByVariant_VariantId(Long variantId);

    Set<Image> findByVariant(Variant variant);

}