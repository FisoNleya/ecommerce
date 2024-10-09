package com.manica.productscatalogue.wardrope;

import java.util.List;

public record WardRopeRequest(
        List<Long> variantIds
) {

}
