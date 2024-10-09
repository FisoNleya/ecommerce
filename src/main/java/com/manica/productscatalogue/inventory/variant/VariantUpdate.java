package com.manica.productscatalogue.inventory.variant;

import java.math.BigDecimal;

public record VariantUpdate(
        String variantName,

        String description,

        String size,

        Boolean active,

        Boolean adminApproved,

        String colorHex,

        int inventoryQuantity,

        BigDecimal price

        ) {
}
