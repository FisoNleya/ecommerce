package com.manica.productscatalogue.wishlist;

import java.util.List;

public record WishListRequest(
        List<Long> variantIds
) {
}
