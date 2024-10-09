package com.manica.productscatalogue.ordering.order.notifications;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderNotificationDto {

    private String orderNumber;
    private List<OrderItemNotification> orderItems;
    private BigDecimal totalPrice;
    private UserNotificationDto user;
}
