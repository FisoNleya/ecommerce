package com.manica.productscatalogue.subscriptions.vendor;



public record VendorRequest(

        Long vendorId,

        String vendorName,

        boolean enabled,

        String tradeName,

        String email,
        String phoneNumber,
        String telephone,
        String taxClearance,
        String logoUrl,
        String addressLine1,
        String city,
        String addressLine2,
        String province,
        String country


) {
}
