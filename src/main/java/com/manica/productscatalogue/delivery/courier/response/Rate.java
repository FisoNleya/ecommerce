
package com.manica.productscatalogue.delivery.courier.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@JsonPropertyOrder({
    "rate",
    "rate_excluding_vat",
    "base_rate",
    "service_level",
    "surcharges",
    "rate_adjustments",
    "time_based_rate_adjustments",
    "extras",
    "charged_weight",
    "actual_weight",
    "volumetric_weight"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rate {

    @JsonProperty("rate")
    public Double rate;
    @JsonProperty("rate_excluding_vat")
    public Double rateExcludingVat;
    @JsonProperty("base_rate")
    public BaseRate baseRate;
    @JsonProperty("service_level")
    public ServiceLevel serviceLevel;
    @JsonProperty("surcharges")
    public List<Object> surcharges;
    @JsonProperty("rate_adjustments")
    public List<Object> rateAdjustments;
    @JsonProperty("time_based_rate_adjustments")
    public List<Object> timeBasedRateAdjustments;
    @JsonProperty("extras")
    public List<Extra> extras;
    @JsonProperty("charged_weight")
    public Double chargedWeight;
    @JsonProperty("actual_weight")
    public Double actualWeight;
    @JsonProperty("volumetric_weight")
    public Double volumetricWeight;


}
