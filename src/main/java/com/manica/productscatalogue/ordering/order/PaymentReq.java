package com.manica.productscatalogue.ordering.order;

import com.manica.productscatalogue.ordering.payment.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentReq(

        @Min(1)
        @Schema(example = "200")
        @NotNull(message = "Please specify subTotal")
        BigDecimal subTotal,


        @Min(1)
        @Schema(example = "10")
        @NotNull(message = "Please specify shipping")
        BigDecimal shipping,

        @Min(1)
        @Schema(example = "5")
        @NotNull(message = "Please specify tax")
        BigDecimal tax,

        @Min(1)
        @Schema(example = "215")
        @NotNull(message = "Please specify total")
        BigDecimal total,

        @NotNull(message = "Please specify payment method")
        PaymentMethod paymentMethod,

        PaypalDetails paypalDetails
) {
}
