package com.manica.productscatalogue.inventory.rating;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.manica.productscatalogue.inventory.variant.Variant;
import com.manica.productscatalogue.subscriptions.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rating",uniqueConstraints = {
        @UniqueConstraint(name = "unique_variant_rating",columnNames = {"variant_id","user_id"})
})
public class Rating {

    @Id
    @GeneratedValue
    @Column(name = "rating_id")
    private Long ratingId;

    @Column(name = "rating_start_value")
    private byte value;

    @Column(columnDefinition = "TEXT")
    private String comment;


    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    @JoinColumn(name = "variant_id",
            nullable = false,
            referencedColumnName = "variant_id",
            foreignKey = @ForeignKey(name = "variant_id_fk"))
    private Variant variant;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id",
            nullable = true,
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name = "user_id_fk"))
    private User createdBy;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Rating rating = (Rating) o;
        return getRatingId() != null && Objects.equals(getRatingId(), rating.getRatingId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
