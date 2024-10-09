package com.manica.productscatalogue.delivery;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/api/v1/delivery-option")
@RequiredArgsConstructor
public class DeliveryOptionController {

    private final DeliveryOptionService deliveryOptionService;



    @PostMapping
    public ResponseEntity<DeliveryOption> add(
            Principal principal,
            @RequestBody @Valid DeliveryOptionReq request
    ) {

        return new ResponseEntity<>(deliveryOptionService.add(request), HttpStatus.CREATED);
    }


    @Operation(deprecated = true, description = "No longer used to get delivery options during check out process , use /delivery-options under ordering")
    @GetMapping
    public ResponseEntity<List<DeliveryOption>> get(
    ) {
        return new ResponseEntity<>(deliveryOptionService.findAll(), HttpStatus.OK);
    }



}
