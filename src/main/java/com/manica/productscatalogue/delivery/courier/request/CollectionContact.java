
package com.manica.productscatalogue.delivery.courier.request;



import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonPropertyOrder({
    "name",
    "mobile_number",
    "email"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionContact {

    @JsonProperty("name")
    public String name;
    @JsonProperty("mobile_number")
    public String mobileNumber;
    @JsonProperty("email")
    public String email;

}
