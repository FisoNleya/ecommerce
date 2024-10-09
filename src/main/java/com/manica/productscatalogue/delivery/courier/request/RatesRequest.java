
package com.manica.productscatalogue.delivery.courier.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@JsonPropertyOrder({
    "collection_address",
    "delivery_address",
    "parcels",
    "declared_value",
    "collection_min_date",
    "delivery_min_date"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatesRequest {

    @JsonProperty("collection_address")
    public CollectionAddress collectionAddress;
    @JsonProperty("delivery_address")
    public DeliveryAddress deliveryAddress;
    @JsonProperty("parcels")
    public List<Parcel> parcels;
    @JsonProperty("declared_value")
    public Integer declaredValue;
    @JsonProperty("collection_min_date")
    public String collectionMinDate;
    @JsonProperty("delivery_min_date")
    public String deliveryMinDate;

}
