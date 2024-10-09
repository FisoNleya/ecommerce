package com.manica.productscatalogue.delivery;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.nio.Buffer;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateResponse {

    private Double rate;

    private String name;

    private String rateId;

    private String rateCode;

    private String deliveryDateFrom;

    private String deliveryDateTo;

    private String collectionDate;


}
