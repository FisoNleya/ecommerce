package com.manica.productscatalogue.ordering.cart;

public record CartItemRequest(

         long quantity ,

         long variantId
) {
}
