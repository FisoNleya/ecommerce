//package com.manica.productscatalogue.inventory.variant;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.Objects;
//
//
//
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class AdminVariantService {
//
//    private final VariantRepository variantRepository;
//
//    private final VariantService variantService;
//
//    public Page<Variant> findAllByVendor(long vendorId, Boolean active, Pageable pageable) {
//
//        if (Objects.isNull(active)) {
//            return variantService.map(variantRepository.findAllByProduct_Vendor_VendorIdAndActiveAndAdminApproved(vendorId, true,true, pageable));
//        }
//        return  variantService.map(variantRepository.findAllByProduct_Vendor_VendorIdAndAdminApproved(vendorId, true, pageable));
//
//    }
//
//
//    public void deleteById(long variantId) {
//        variantRepository.deleteById(variantId);
//    }
//
//
//
//}
