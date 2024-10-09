package com.manica.productscatalogue.ordering.order;


import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import com.manica.productscatalogue.exceptions.DataNotFoundException;
import com.manica.productscatalogue.exceptions.DuplicateRecordException;
import com.manica.productscatalogue.exceptions.InvalidRequestException;
import com.manica.productscatalogue.ordering.CartItemsRequest;
import com.manica.productscatalogue.ordering.cart.CartItem;
import com.manica.productscatalogue.ordering.cart.CartService;
import com.manica.productscatalogue.ordering.contact.Contact;
import com.manica.productscatalogue.ordering.contact.ContactRequest;
import com.manica.productscatalogue.ordering.contact.ContactService;
import com.manica.productscatalogue.delivery.DeliveryOption;
import com.manica.productscatalogue.delivery.DeliveryOptionResponse;
import com.manica.productscatalogue.delivery.DeliveryOptionService;
import com.manica.productscatalogue.ordering.payment.Payment;
import com.manica.productscatalogue.ordering.uttils.Unique;
import com.manica.productscatalogue.ordering.uttils.UniqueRepository;
import com.manica.productscatalogue.subscriptions.user.User;
import com.manica.productscatalogue.subscriptions.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UniqueRepository uniqueRepository;

    private final UserService userService;
    private final CartService cartService;

    private final ContactService contactService;
    private final DeliveryOptionService deliveryOptionService;
    private final OrderSpecification orderSpecification;


    private static final String ORD_PREFIX = "ORD";

    private final ApplicationEventPublisher publisher;



    Order createOrder(CartItemsRequest request, Principal principal) {


        User currUser = null;

        if(principal == null && request.guest() == null) {
            throw new InvalidRequestException("User must be authenticated or provide guest details");
        }

        if(principal == null) {
            currUser = userService.findUserByEmailOrCreate(request.guest());

        }else {
            var authUser = (AuthenticationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
            currUser = userService.findUserByEmailOrCreate(authUser);
        }


        if(Objects.isNull(currUser)) {
            throw new IllegalStateException("User must be authenticated or provide guest details, request failed internally");
        }



        List<CartItem> cartItems = cartService.addCartItems(request, true, currUser.getEmail());

        Order order = orderRepository.save(Order.builder()
                .subTotal(cartItems.get(0).getCart().getTotalPrice())
                .orderId(generateOrderNumber())
                .paid(false)
                .orderStatus(OrderStatus.IN_COMPLETE)
                .owner(currUser)
                .build());


        List<OrderItem> orderItems = cartItems.stream().map(cartItem ->
                OrderItem.builder()
                        .cartItem(cartItem)
                        .order(order)
                        .usedPrice(cartItem.getPrice())
                        .build()
        ).toList();

        orderItems = orderItemRepository.saveAll(orderItems);
        order.setOrderItems(orderItems);

        return order;

    }


    Order addContact(ContactRequest request, String orderId) {

        Contact contact = contactService.add(request);
        Order order = findOrderById(orderId);

        order.setDeliveryContact(contact);
        return orderRepository.save(order);
    }

    public Order addDeliveryOption(ShippingRequest request, long deliveryOptionId, String orderId) {

        DeliveryOption deliveryOption = deliveryOptionService.findDeliveryOptionById(deliveryOptionId);
        Order order = findOrderById(orderId);
        order.setDelivery(deliveryOption);

        addShipping(request, order, deliveryOptionId);

        calculateOrderValues(order);
        return orderRepository.save(order);
    }


    private void calculateOrderValues(Order order){

        BigDecimal orderTotal = BigDecimal.ZERO;

        BigDecimal tax = BigDecimal.ZERO;// To get tax collection entity;
        BigDecimal shippingAmount = order.getShipping().getRate();
        BigDecimal itemSubTotal =  order.getSubTotal();

        orderTotal = orderTotal.add(tax).add(shippingAmount).add(itemSubTotal);

        order.setShippingAmount(shippingAmount);
        order.setTax(tax);
        order.setSubTotal(itemSubTotal);
        order.setTotalPrice(orderTotal);


    }

    private void  addShipping(ShippingRequest request, Order order, long deliveryOptionId) {
        order.setShipping(Shipping.builder()
                .rate(request.rate())
                .serviceName(request.serviceName())
                .serviceId(request.serviceId())
                .serviceCode(request.serviceCode())
                .specialCollectionInstructions(request.specialCollectionInstructions())
                .specialDeliveryInstructions(request.specialDeliveryInstructions())
                .deliveryOptionId(deliveryOptionId)
                .build());
    }


    synchronized Order pay(PaymentReq request, String orderId) {

        Order order = findOrderById(orderId);

        if(order.getOrderStatus() == OrderStatus.PROCESSING) // and other statuses after processing
            throw new DuplicateRecordException("Order is already being process , please be patient "+ order.getOrderId());

        if (order.getShipping() ==null || order.getDelivery() == null)
            throw new InvalidRequestException("Please specify shipping details for this order : "+ order.getOrderId());

        if (order.getDeliveryContact() == null)
            throw new DataNotFoundException("Delivery contact not found for this order : " + order.getOrderId());


        order.setPayment(Payment.builder()
                .totalAmountPaid(order.getTotalPrice())
                .successful(true)
                .method(request.paymentMethod())
                .cardLast4Digits(request.paypalDetails().cardNumber())
                .build());
        order = orderRepository.save(order);

        List<OrderItem> orderItems =  orderItemRepository.findByOrder_OrderId(orderId);
        order.setOrderItems(orderItems);

        publisher.publishEvent(order);

        //deactivate / clear cart
        //Create shipment
        //send notification


        //update inventory
        order.setOrderStatus(OrderStatus.PROCESSING);
        order = orderRepository.save(order);

        return order;
    }




    List<DeliveryOptionResponse> getDeliveryOptions(String orderId) {
        Order order = findOrderById(orderId);
        return deliveryOptionService.findAllOptions(order);
    }


    Order findOrderById(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found with id: " + orderId));

        List<OrderItem> orders =  orderItemRepository.findByOrder_OrderId(orderId);
        order.setOrderItems(orders);
        return order;
    }

    Page<Order> getOrders(OrderSearchDto searchDto, Pageable pageable) {

        var orderSpec = orderSpecification.getOrderQuerySpecification(searchDto);
        return orderRepository.findAll(orderSpec, pageable);
    }

    synchronized String generateOrderNumber() {
        return ORD_PREFIX + String.format("%06d", uniqueRepository.save(new Unique()).getUniqueId())
                //    + UUID.randomUUID().toString()
                .replaceAll("-", "");
    }


}
