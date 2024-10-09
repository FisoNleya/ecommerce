package com.manica.productscatalogue.delivery;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;


@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "delivery_option")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DeliveryOption {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long deliveryId;

    @Column(name = "trade_name", unique = true , nullable = false)
    private String optionTradeName;

    @Column(name = "option_logo_url", columnDefinition = "TEXT")
    private String optionLogoUrl;

    private String phoneNumber;

    private String addressLine1;

    private String addressLine2;


    private String city;

    private String province;

    private String country;

    @CreatedBy
    private String createdBy;


    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        DeliveryOption that = (DeliveryOption) object;
        return getDeliveryId() != null && Objects.equals(getDeliveryId(), that.getDeliveryId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
