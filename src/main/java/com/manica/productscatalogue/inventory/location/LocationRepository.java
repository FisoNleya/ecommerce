package com.manica.productscatalogue.inventory.location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByVendor_VendorIdIn(List<Long> vendorIds);

    List<Location> findByUser_Email(String email);

}