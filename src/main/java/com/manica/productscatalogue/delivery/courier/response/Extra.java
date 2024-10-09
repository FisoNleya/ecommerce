
package com.manica.productscatalogue.delivery.courier.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonPropertyOrder({
    "id",
    "insurance_charge",
    "vat",
    "vat_type"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Extra {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("insurance_charge")
    public Integer insuranceCharge;
    @JsonProperty("vat")
    public Double vat;
    @JsonProperty("vat_type")
    public String vatType;


}
