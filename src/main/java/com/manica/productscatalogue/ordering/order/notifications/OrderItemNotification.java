package com.manica.productscatalogue.ordering.order.notifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemNotification {

    private String itemName;
    private long quantity;
    private BigDecimal unitPrice;
    private BigDecimal rowTotal;


}
