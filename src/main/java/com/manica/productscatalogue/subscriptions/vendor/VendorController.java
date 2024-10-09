package com.manica.productscatalogue.subscriptions.vendor;

import com.manica.productscatalogue.subscriptions.vendor.Vendor;
import com.manica.productscatalogue.subscriptions.vendor.VendorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/v1/vendor")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;


    @Operation(
            description = "Test endpoint to add vendors",
            summary = "This end point will be removed",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )
    @PostMapping
    public ResponseEntity<Vendor> add(
            Principal principal,
            @RequestBody @Valid VendorRequest request
    ) {

        return new ResponseEntity<>(vendorService.add(request), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Vendor>> get(
    ) {
        return new ResponseEntity<>(vendorService.findAll(), HttpStatus.OK);
    }


}
