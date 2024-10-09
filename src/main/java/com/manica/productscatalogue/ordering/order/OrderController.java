package com.manica.productscatalogue.ordering.order;


import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import com.manica.productscatalogue.ordering.CartItemsRequest;
import com.manica.productscatalogue.ordering.contact.ContactRequest;
import com.manica.productscatalogue.delivery.DeliveryOptionResponse;
import com.manica.productscatalogue.ordering.payment.PaymentMethod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
 class OrderController {


    private final OrderService orderService;


    @PostMapping
     ResponseEntity<Order> add(
            @RequestBody CartItemsRequest request,
            Principal principal
    ) {

        return new ResponseEntity<>(orderService.createOrder(request, principal), HttpStatus.CREATED);
    }

    //----------------- start unsecure
    @PutMapping("/{id}/contact")
     ResponseEntity<Order> addContact(
            @RequestBody @Valid ContactRequest request,
            @PathVariable(name = "id") @Parameter(example = "ORD00001") String orderId
    ) {


        return new ResponseEntity<>(orderService.addContact(request, orderId), HttpStatus.OK);
    }

    @PutMapping("/{id}/delivery/{delivery-id}")
     ResponseEntity<Order> addDelivery(
            @PathVariable(name = "id") @Parameter(example = "ORD00001") String orderId,
            @PathVariable(name = "delivery-id") @Parameter(example = "1") long deliveryId,
            @RequestBody @Valid ShippingRequest request
    ) {


        return new ResponseEntity<>(orderService.addDeliveryOption(request, deliveryId, orderId), HttpStatus.OK);
    }

    @PutMapping("/{id}/payment")
     ResponseEntity<Order> addPayment(
            @PathVariable(name = "id") @Parameter(example = "ORD00001") String orderId,
            @RequestBody @Valid PaymentReq request,
            Principal principal
    ) {


        return new ResponseEntity<>(orderService.pay(request, orderId), HttpStatus.OK);
    }

    //-----------------end unsecure


    @GetMapping("/{id}/delivery-options")
     ResponseEntity<List<DeliveryOptionResponse>> getDeliveryOptions(
            @PathVariable(name = "id") @Parameter(example = "ORD00001") String orderId
    ) {


        return new ResponseEntity<>(orderService.getDeliveryOptions(orderId), HttpStatus.OK);
    }


    @GetMapping("/{id}")
     ResponseEntity<Order> getOrder(
            @PathVariable(name = "id") @Parameter(example = "ORD00001") String orderId
    ) {


        return new ResponseEntity<>(orderService.findOrderById(orderId), HttpStatus.OK);
    }


    @Operation(
            description = "Get All status values ",
            summary = "Get Order Status Enum Values",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )
    @GetMapping("/statuses")
     ResponseEntity<OrderStatus[]> findAllStatuses(


    ) {
        return new ResponseEntity<>(OrderStatus.values(), HttpStatus.OK);
    }

    @Operation(
            description = "Get All Payment Method values ",
            summary = "Get Payment Method Enum Values",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )
    @GetMapping("/payment-methods")
     ResponseEntity<PaymentMethod[]> findAllPaymentMethods(


    ) {
        return new ResponseEntity<>(PaymentMethod.values(), HttpStatus.OK);
    }


    @GetMapping
     ResponseEntity<Page<Order>> findAll(
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(required = false, defaultValue = "") @Parameter(example = "tariro@gmail.com", description = "Customer email") String ownerEmail,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) @Parameter(example = "2024-06-01T00:30") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @Parameter(example = "2024-08-01T00:30") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to


    ) {

        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return new ResponseEntity<>(orderService.getOrders(OrderSearchDto.builder()
                .ownerEmail(ownerEmail)
                .status(status)
                .from(from)
                .to(to)
                .build(), pageable), HttpStatus.OK);
    }


}
