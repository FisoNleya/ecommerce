package com.manica.productscatalogue.delivery;

public record DeliveryOptionReq(

        String optionTradeName,

        String optionLogoUrl,

        String phoneNumber,

        String addressLine1,

        String addressLine2,


        String city,

        String province,

        String country
        
) {
}
