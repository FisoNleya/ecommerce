package com.manica.productscatalogue.inventory.variant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface VariantRepository extends JpaRepository<Variant, Long>, JpaSpecificationExecutor<Variant> {

    Optional<Variant> findBySizeAndColorHexAndProduct_ProductId(String size, String colorHex, long productId);

    List<Variant> findByProduct_ProductId(long productId);

    Page<Variant> findAllByProduct_ProductIdAndActiveAndVariantIdNotAndAdminApproved(long productId, boolean active,long variantId, boolean adminApproved, Pageable pageable);

    Page<Variant> findAllByProduct_Vendor_VendorIdAndActiveAndAdminApproved (long vendorId, boolean active, boolean adminApproved, Pageable pageable);

    Page<Variant> findAllByProduct_Vendor_VendorIdAndAdminApproved (long vendorId, boolean adminApproved, Pageable pageable);


    Optional<Variant> findByVariantIdAndProduct_Owner_Email(long variantId, String email);

    Optional<Variant> findByVariantIdAndProduct_Vendor_VendorId(long variantId, long vendorId);

    Optional<Variant> findFirstByColorAndVariantIdNotOrColorHexAndVariantIdNot(String color, long variantId, String hex, long variantId2);

    Optional<Variant> findFirstBySizeAndProduct_ProductId(String size, long productId);


    List<Variant> findByVariantIdInAndActive(List<Long> variantIds, boolean active);

    //VENDOR
    Page<Variant> findAllByProduct_Vendor_VendorId(long vendorId, Pageable pageable);

    //ADMIN
    List<Variant> findByVariantIdIn(List<Long> variantIds);

}