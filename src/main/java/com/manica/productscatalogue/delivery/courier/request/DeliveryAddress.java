
package com.manica.productscatalogue.delivery.courier.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonPropertyOrder({
    "type",
    "company",
    "street_address",
    "local_area",
    "city",
    "zone",
    "country",
    "code",
    "lat",
    "lng"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddress {

    @JsonProperty("type")
    public String type;
    @JsonProperty("company")
    public String company;
    @JsonProperty("street_address")
    public String streetAddress;
    @JsonProperty("local_area")
    public String localArea;
    @JsonProperty("city")
    public String city;
    @JsonProperty("zone")
    public String zone;
    @JsonProperty("country")
    public String country;
    @JsonProperty("code")
    public String code;
    @JsonProperty("lat")
    public Double lat;
    @JsonProperty("lng")
    public Double lng;

}
