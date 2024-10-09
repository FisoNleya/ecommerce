package com.manica.productscatalogue.inventory;


import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class SearchDTO {

    private List<String> brands;
    private List<String> categories;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Byte rating;
    private String search;

    private String productName;

    private String description;

    private String sku;

    private String vendorName;

    private String ownerName;

}
