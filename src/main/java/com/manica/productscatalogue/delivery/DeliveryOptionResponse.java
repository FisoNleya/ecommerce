package com.manica.productscatalogue.delivery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryOptionResponse {

    private Long deliveryId;

    private String optionTradeName;

    private String optionLogoUrl;

    private String phoneNumber;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String province;
    private List<RateResponse> rates;



}
