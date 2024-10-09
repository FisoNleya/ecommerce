
package com.manica.productscatalogue.delivery.courier.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonPropertyOrder({
    "id",
    "code",
    "name",
    "description",
    "delivery_date_from",
    "delivery_date_to",
    "collection_date",
    "collection_cut_off_time",
    "vat_type",
    "insurance_disabled"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceLevel {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("code")
    public String code;
    @JsonProperty("name")
    public String name;
    @JsonProperty("description")
    public String description;
    @JsonProperty("delivery_date_from")
    public String deliveryDateFrom;
    @JsonProperty("delivery_date_to")
    public String deliveryDateTo;
    @JsonProperty("collection_date")
    public String collectionDate;
    @JsonProperty("collection_cut_off_time")
    public String collectionCutOffTime;
    @JsonProperty("vat_type")
    public String vatType;
    @JsonProperty("insurance_disabled")
    public Boolean insuranceDisabled;

}
