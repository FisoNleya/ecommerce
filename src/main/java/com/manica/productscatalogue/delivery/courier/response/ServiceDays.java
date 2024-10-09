
package com.manica.productscatalogue.delivery.courier.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonPropertyOrder({
    "collection_service_days",
    "delivery_service_days"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDays {

    @JsonProperty("collection_service_days")
    public Object collectionServiceDays;
    @JsonProperty("delivery_service_days")
    public Object deliveryServiceDays;

}
