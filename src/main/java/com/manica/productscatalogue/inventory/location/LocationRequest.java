package com.manica.productscatalogue.inventory.location;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record LocationRequest(

         @Schema(example = "Officce 506 Pinoneer House 1188 Lois Avenue")
         @NotEmpty(message = "Please specify address")
         String addressLine1,

         String addressLine2,

         @Schema(example = "1188 Lois Avenue")
         @NotEmpty(message = "Please specify street address")
         String streetAddress,

         @Schema(example = "BUSINESS", defaultValue = "RESIDENTIAL")
        // @NotEmpty(message = "Please specify street address type")
         LocationType addressType,

         @Schema(example = "Menlyn")
         @NotEmpty(message = "Please specify local area")
         String suburb,

         @Schema(example = "Pretoria" )
         @NotEmpty(message = "Please specify city")
         String city,

         @Schema(example = "Gauteng" )
         @NotEmpty(message = "Please specify zone")
         String zone,

         @Schema(example = "0081")
         @NotEmpty(message = "Please specify zip")
         String zipCode,

         @Schema(example = "Gauteng")
         @NotEmpty(message = "Please specify province")
         String province,

         @Schema(defaultValue = "ZA", example = "ZA")
         String country,

         @Schema(example = "uAfrica.com")
         String company
) {
}
