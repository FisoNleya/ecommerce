package com.manica.productscatalogue.delivery.courier;


import com.manica.productscatalogue.delivery.courier.request.RatesRequest;
import com.manica.productscatalogue.delivery.courier.request.ShipmentRequest;
import com.manica.productscatalogue.delivery.courier.response.RatesResponse;
import com.manica.productscatalogue.delivery.courier.response.ShipResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "Courier", url = "${delivery.option.courier.url}")
public interface CourierClient {

    String AUTHORIZATION = "Authorization";
    String BEARER = "Bearer ";

    String RATES = "/rates";

    String SHIPMENTS = "/shipments";

    @PostMapping (value = RATES)
    RatesResponse getRates(@RequestHeader(AUTHORIZATION) String bearerToken, @RequestBody RatesRequest request);


    @PostMapping (value = RATES)
    ShipResponse addShipment(@RequestHeader(AUTHORIZATION) String bearerToken, @RequestBody ShipmentRequest request);

}
