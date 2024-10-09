package com.manica.productscatalogue.inventory.variant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record VariantRequest(


        @Schema(example = "DR-#VHH21")
        @NotEmpty(message = "Please specify  variant sku")
        String variantSku,

        @Schema(example = "Nike Air Jordan Retro Gold")
        @NotEmpty(message = "Please specify  variant name")
        String variantName,

        String description,

        String size,

        String color,

        @Schema(example = " 3.2", description = "Weight in grammes")
        @NotNull(message = "Please specify weight")
        Double weight,

        @Schema(example = "42.5", description = "Length in cm")
        @NotNull(message = "Please specify length")
        Double length,

        @Schema(example = "42.5", description = "Width in cm")
        @NotNull(message = "Please specify width")
        Double width,

        @Schema(example = "5.5", description = "Height in cm")
        @NotNull(message = "Please specify height")
        Double height,



        @Schema(example = "#####")
        @NotEmpty(message = "Please specify  variant color hex")
        String colorHex,

        @Min(1)
        @NotNull(message = "Please specify quantity")
        int inventoryQuantity,

        @Min(1)
        @Schema(example = "100")
        @NotNull(message = "Please specify variant price")
        BigDecimal price,

        @Schema(example = "1")
        @NotNull(message = "Please specify product Id")
        long productId,

        @Schema(example = "1", description = "Location of the ware house /store of the product variant", defaultValue = "1")
        @NotNull(message = "Please specify location  id")
        long locationId

) {

}
