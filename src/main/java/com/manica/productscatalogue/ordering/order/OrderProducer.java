package com.manica.productscatalogue.ordering.order;


import com.manica.productscatalogue.ordering.order.notifications.OrderItemNotification;
import com.manica.productscatalogue.ordering.order.notifications.OrderNotificationDto;
import com.manica.productscatalogue.ordering.order.notifications.UserNotificationDto;
import com.manica.productscatalogue.subscriptions.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderNotificationDto> vendorDtoKafkaTemplate;


    @Value("${topic.comms.order_confirmation}")
    private String orderConfirmationTopic;


    @Async
    @EventListener
    public void onOrderNotifyVendorCreation(Order order){

        List<OrderItemNotification> orderItems = order.getOrderItems().stream()
                .map(orderItem ->{

                    long quantity = orderItem.getCartItem().getQuantity();
                    BigDecimal usedPrice = orderItem.getCartItem().getPrice();
                    BigDecimal rowTotal = usedPrice
                                    .setScale(2, RoundingMode.HALF_EVEN);
                    BigDecimal unitPrice = orderItem.getCartItem().getVariant().getPrice()
                            .setScale(2, RoundingMode.HALF_EVEN);


                 return   OrderItemNotification.builder()
                            .itemName(orderItem.getCartItem().getVariant().getVariantName())
                            .quantity(quantity)
                            .unitPrice(unitPrice) // Unit price is variant unit price
                            .rowTotal(rowTotal) //row total is the price field
                            .build();

                } )
                .collect(Collectors.toList());

        User user  = order.getOwner();

        UserNotificationDto userNotificationDto = new UserNotificationDto();
        userNotificationDto.setEmailAddress(user.getEmail());
        userNotificationDto.setFirstname(user.getFirstName());
        userNotificationDto.setLastname(user.getLastName());

         OrderNotificationDto orderNotificationDto = new OrderNotificationDto();
         orderNotificationDto.setUser(userNotificationDto);
         orderNotificationDto.setOrderNumber(order.getOrderId());
         orderNotificationDto.setOrderItems(orderItems);
         orderNotificationDto.setTotalPrice(order.getTotalPrice());


        final ProducerRecord<String, OrderNotificationDto> producerRecord = new ProducerRecord<>(orderConfirmationTopic,
                orderNotificationDto);
        vendorDtoKafkaTemplate.send(producerRecord);
        log.info("Published order confirmation {}", orderNotificationDto);

    }

}

