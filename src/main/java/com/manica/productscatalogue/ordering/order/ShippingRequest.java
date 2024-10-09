package com.manica.productscatalogue.ordering.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ShippingRequest(

        @Schema(example = "50",description = "Rate in rands")
        @NotNull(message = "Please specify in rands")
        BigDecimal rate,

        @Schema(example = "Local Overnight Flyer",description = "Service name given at GET /delivery-options")
        @NotEmpty(message = "Please specify service name")
        String serviceName,

        @Schema(example = "34849",description = "Optional Service ID given at GET /delivery-options")
        String serviceId,

        @Schema(example = "LOF",description = "Service code given at GET /delivery-options")
        @NotEmpty(message = "Please specify service code")
        String serviceCode,


        @Schema(example = "Glass objects inside , handle with care otherwise they die :)",description = "Optional instructions, These are considered / available for courier guy for now")
        String specialCollectionInstructions,

        @Schema(example = "Glass objects inside , handle with care otherwise they die :)",description = "Optional instructions, These are considered / available for courier guy for now")
        String specialDeliveryInstructions
) {
}
