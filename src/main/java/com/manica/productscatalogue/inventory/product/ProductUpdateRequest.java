package com.manica.productscatalogue.inventory.product;

import java.math.BigDecimal;

public record ProductUpdateRequest(

        String productName,

        String description,

        String sku,

        BigDecimal regularPrice,

        int quantity,

        String extendedDescription

) {
}
