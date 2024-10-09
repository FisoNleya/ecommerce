package com.manica.productscatalogue.ordering.order;

import java.math.BigDecimal;

public record OrderRequest (
        String orderId,
                
        OrderStatus orderStatus,

        BigDecimal totalPrice

){
}
