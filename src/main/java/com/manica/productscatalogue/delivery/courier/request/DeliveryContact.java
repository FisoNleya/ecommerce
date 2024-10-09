
package com.manica.productscatalogue.delivery.courier.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "mobile_number",
    "email"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryContact {

    @JsonProperty("name")
    public String name;
    @JsonProperty("mobile_number")
    public String mobileNumber;
    @JsonProperty("email")
    public String email;

}
