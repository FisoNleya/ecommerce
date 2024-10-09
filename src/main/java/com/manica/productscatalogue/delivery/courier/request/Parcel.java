
package com.manica.productscatalogue.delivery.courier.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonPropertyOrder({
        "parcel_description",
        "submitted_length_cm",
        "submitted_width_cm",
        "submitted_height_cm",
        "submitted_weight_kg"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parcel {

    @JsonProperty("submitted_length_cm")
    public Double submittedLengthCm;
    @JsonProperty("submitted_width_cm")
    public Double submittedWidthCm;
    @JsonProperty("submitted_height_cm")
    public Double submittedHeightCm;
    @JsonProperty("submitted_weight_kg")
    public Double submittedWeightKg;


    @JsonProperty("parcel_description")
    public String parcelDescription;


}
