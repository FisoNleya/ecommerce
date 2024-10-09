package com.manica.productscatalogue.inventory.variant;

import java.util.List;

public record VariantVerification(

        List<Long> variantIds,
        Boolean verifyAllProductVariants

) {
}
