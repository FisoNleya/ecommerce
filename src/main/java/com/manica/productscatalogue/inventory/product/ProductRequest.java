package com.manica.productscatalogue.inventory.product;

import java.math.BigDecimal;

public record ProductRequest(

         String productName,

         String description,

         String sku,

         BigDecimal regularPrice,

         int quantity,

         String extendedDescription,

         long brandId,

         long categoryId,

         Long vendorId,

         String ownerId

) {
}
