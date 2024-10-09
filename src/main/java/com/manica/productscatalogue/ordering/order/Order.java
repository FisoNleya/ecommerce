package com.manica.productscatalogue.ordering.order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.manica.productscatalogue.delivery.DeliveryOption;
import com.manica.productscatalogue.ordering.contact.Contact;
import com.manica.productscatalogue.ordering.payment.Payment;
import com.manica.productscatalogue.subscriptions.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "product_order")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @Column(name = "order_id", unique = true)
    private String orderId;


    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private boolean paid;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "delivery_id",
            nullable = true,
            referencedColumnName = "delivery_id",
            foreignKey = @ForeignKey(name = "delivery_id_fk"))
    private DeliveryOption delivery;


    @OneToOne(cascade = CascadeType.PERSIST)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "shipping_id",
            nullable = true,
            referencedColumnName = "shipping_id",
            foreignKey = @ForeignKey(name = "shipping_id_fk"))
    private Shipping shipping;



    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "contact_id",
            nullable = true,
            referencedColumnName = "contact_id",
            foreignKey = @ForeignKey(name = "contact_id_fk"))
    private Contact deliveryContact;


    private BigDecimal shippingAmount;

    private BigDecimal tax;

    private BigDecimal subTotal;

    private BigDecimal totalPrice;


    @CreationTimestamp
    private LocalDateTime createdAt;


    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @CreatedBy
    private String createdBy;


    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @JsonManagedReference
    @ToString.Exclude
    private List<OrderItem> orderItems;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id",
            nullable = false,
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name = "user_id_fk"))
    private User owner;


    @JsonManagedReference
    @OneToOne(cascade = CascadeType.PERSIST)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "payment_id",
            nullable = true,
            referencedColumnName = "payment_id",
            foreignKey = @ForeignKey(name = "payment_id_fk"))
    private Payment payment;

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Order order = (Order) object;
        return getOrderId() != null && Objects.equals(getOrderId(), order.getOrderId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
