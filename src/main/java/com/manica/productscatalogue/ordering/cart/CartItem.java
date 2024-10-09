package com.manica.productscatalogue.ordering.cart;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.manica.productscatalogue.inventory.variant.Variant;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "cart_item")
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long itemId;

    private long quantity ;

    private BigDecimal price;

    private BigDecimal unitPrice;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "variant_id",
            nullable = false,
            referencedColumnName = "variant_id",
            foreignKey = @ForeignKey(name = "variant_id_fk"))
    private Variant variant;



    @ManyToOne
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cart_id",
            nullable = false,
            referencedColumnName = "cart_id",
            foreignKey = @ForeignKey(name = "cart_id_fk"))
    private Cart cart;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @CreatedBy
    private String createdBy;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CartItem cartItem = (CartItem) o;
        return getItemId() != null && Objects.equals(getItemId(), cartItem.getItemId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
