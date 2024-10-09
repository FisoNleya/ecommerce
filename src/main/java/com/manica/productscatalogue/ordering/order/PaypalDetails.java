package com.manica.productscatalogue.ordering.order;

import java.time.LocalDate;

public record PaypalDetails (

        String cardNumber,
        String cardHolderName,
        String expirationDate,
        Integer ccv

) {
}
