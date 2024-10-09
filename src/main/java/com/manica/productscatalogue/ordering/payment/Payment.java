package com.manica.productscatalogue.ordering.payment;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.manica.productscatalogue.ordering.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Data
@Entity
@Builder
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Payment {

    @Id
    @GeneratedValue
    @Column(name = "payment_id")
    private Long paymentId;

    private BigDecimal totalAmountPaid;

    private boolean successful;

    private PaymentMethod method;

    private String cardLast4Digits;

    @JsonBackReference
    @OneToOne(mappedBy = "payment")
    private Order order;


    @CreatedBy
    private String createdBy;


}
