package com.manica.productscatalogue.ordering.contact;

import com.manica.productscatalogue.inventory.location.LocationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record ContactRequest(

        @Schema(example = "Alan")
        @NotEmpty(message = "Please specify First Name")
        String firstName,

        String lastName,

        @Schema(example = "alan@gmail.com")
        @NotEmpty(message = "Please specify email")
        String email,

        @Schema(example = "0792834934")
        @NotEmpty(message = "Please specify phoneNumber")
        String phoneNumber1,


        String phoneNumber2,

        @Schema(example = "Officce 506 Pinoneer House 1188 Lois Avenue")
        @NotEmpty(message = "Please specify address")
        String addressLine1,


        String addressLine2,

        @Schema(example = "10 Midas Avenue")
        @NotEmpty(message = "Please specify street address")
        String streetAddress,

        @Schema(example = "Olympus AH")
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

        @Schema(example = "ZA", defaultValue = "ZA")
        String country,

        @Schema(example = "RESIDENTIAL", defaultValue = "RESIDENTIAL")
        LocationType addressType,

        @Schema(example = "uAfrica.com", defaultValue = "")
        String company

) {
}
