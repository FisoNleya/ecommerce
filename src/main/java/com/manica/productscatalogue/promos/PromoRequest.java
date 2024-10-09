package com.manica.productscatalogue.promos;


import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record PromoRequest(

        String promoName,

        String promoCode,

        String promoDescription,


        @Schema(example = "2024-06-01T00:30")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDate startDate,

        boolean isAdminApproved,

        @Schema(example = "2024-08-01T00:30")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDate endDate


) {
}
