package com.manica.productscatalogue.ordering.order;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderSearchDto {

    private String ownerEmail;

    private OrderStatus status;

    private LocalDateTime from;

    private LocalDateTime to;

}
