package com.manica.productscatalogue.ordering;

import com.manica.productscatalogue.ordering.cart.CartItemRequest;
import com.manica.productscatalogue.ordering.order.GuestRequest;
import com.messaging.UserDto;

import java.util.List;

public record CartItemsRequest(
        List<CartItemRequest> cartItems,

        GuestRequest guest
) {
}
