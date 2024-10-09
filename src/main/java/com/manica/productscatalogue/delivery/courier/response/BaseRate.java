package com.manica.productscatalogue.delivery.courier.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@JsonPropertyOrder({
    "charge_per_parcel",
    "charge",
    "group_name",
    "vat",
    "vat_type",
    "rate_formula_type",
    "total_calculated_weight"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseRate {

    @JsonProperty("charge_per_parcel")
    public List<Double> chargePerParcel;
    @JsonProperty("charge")
    public Integer charge;
    @JsonProperty("group_name")
    public String groupName;
    @JsonProperty("vat")
    public Double vat;
    @JsonProperty("vat_type")
    public String vatType;
    @JsonProperty("rate_formula_type")
    public String rateFormulaType;
    @JsonProperty("total_calculated_weight")
    public Integer totalCalculatedWeight;


}
