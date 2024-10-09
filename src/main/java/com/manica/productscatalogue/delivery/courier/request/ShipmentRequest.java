
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
    "collection_contact",
    "delivery_address",
    "delivery_contact",
    "parcels",
    "opt_in_rates",
    "opt_in_time_based_rates",
    "special_instructions_collection",
    "special_instructions_delivery",
    "declared_value",
    "collection_min_date",
    "collection_after",
    "collection_before",
    "delivery_min_date",
    "delivery_after",
    "delivery_before",
    "custom_tracking_reference",
    "customer_reference",
    "service_level_code",
    "mute_notifications"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentRequest {

    @JsonProperty("collection_address")
    public CollectionAddress collectionAddress;
    @JsonProperty("collection_contact")
    public CollectionContact collectionContact;
    @JsonProperty("delivery_address")
    public DeliveryAddress deliveryAddress;
    @JsonProperty("delivery_contact")
    public DeliveryContact deliveryContact;
    @JsonProperty("parcels")
    public List<Parcel> parcels;
    @JsonProperty("opt_in_rates")
    public List<Object> optInRates;
    @JsonProperty("opt_in_time_based_rates")
    public List<Integer> optInTimeBasedRates;
    @JsonProperty("special_instructions_collection")
    public String specialInstructionsCollection;
    @JsonProperty("special_instructions_delivery")
    public String specialInstructionsDelivery;
    @JsonProperty("declared_value")
    public Integer declaredValue;
    @JsonProperty("collection_min_date")
    public String collectionMinDate;
    @JsonProperty("collection_after")
    public String collectionAfter;
    @JsonProperty("collection_before")
    public String collectionBefore;
    @JsonProperty("delivery_min_date")
    public String deliveryMinDate;
    @JsonProperty("delivery_after")
    public String deliveryAfter;
    @JsonProperty("delivery_before")
    public String deliveryBefore;
    @JsonProperty("custom_tracking_reference")
    public String customTrackingReference;
    @JsonProperty("customer_reference")
    public String customerReference;
    @JsonProperty("service_level_code")
    public String serviceLevelCode;
    @JsonProperty("mute_notifications")
    public Boolean muteNotifications;

}
