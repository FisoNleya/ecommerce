
package com.manica.productscatalogue.delivery.courier.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@JsonPropertyOrder({
    "message",
    "service_days",
    "rates"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatesResponse {

    @JsonProperty("message")
    public String message;
    @JsonProperty("service_days")
    public ServiceDays serviceDays;
    @JsonProperty("rates")
    public List<Rate> rates;


}
