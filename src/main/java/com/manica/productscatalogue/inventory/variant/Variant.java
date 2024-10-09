package com.manica.productscatalogue.inventory.variant;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.manica.productscatalogue.inventory.Image;
import com.manica.productscatalogue.inventory.features.VariantFeature;
import com.manica.productscatalogue.inventory.location.Location;
import com.manica.productscatalogue.inventory.product.Product;
import com.manica.productscatalogue.inventory.rating.Rating;
import com.manica.productscatalogue.promos.Promo;
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
import java.util.Set;


@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "variant",uniqueConstraints = {
        @UniqueConstraint(name = "unique_product_variant",columnNames = {"product_id","variant_size","variant_color"})
})
public class Variant {

        @Id
        @GeneratedValue
        @Column(name = "variant_id")
        private Long variantId;

        private String variantName;

        private String description;

        @Column(name = "variant_size")
        private String size;

        //Required features

        @Column(name = "variant_color")
        private String color;

        private Double weight;

        private Double length;
        private Double height;
        private Double width;



        //Vendor allowed to activate and deactivate product variants for listing
        private Boolean active;

        //Admin allowed to approve product variants for listing
        private Boolean  adminApproved;

        //Hide products on special and on offer , is user does not have feature
        private Boolean hidden;

        //Count the number of sales of this product variant
        private Integer sellCount;

        private String colorHex;

        private int inventoryQuantity;

        private String variantSku;

        private BigDecimal price;



        @JsonIgnore
        //Features enabled for this product;
        @ElementCollection(targetClass=String.class, fetch=FetchType.EAGER)
        private List<String> systemFeatures;



        @OneToMany(mappedBy = "variant", fetch = FetchType.EAGER)
        @JsonManagedReference
        @ToString.Exclude
        private Set<Image> images ;


        @OneToMany(mappedBy = "variant", fetch = FetchType.EAGER)
        @JsonManagedReference
        @ToString.Exclude
        private Set<VariantFeature> variantFeatures;


        @ManyToOne
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JoinColumn(name = "location_id",
                referencedColumnName = "location_id",
                foreignKey = @ForeignKey(name = "location_id_fk"))
        private Location location;



        @ManyToOne
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JoinColumn(name = "product_id",
                nullable = false,
                referencedColumnName = "product_id",
                foreignKey = @ForeignKey(name = "product_id_fk"))
        private Product product;

        @OneToMany(mappedBy = "variant")
        @JsonBackReference
        @ToString.Exclude
        private Set<Rating> ratings;

        @OneToMany(mappedBy = "variant")
        @JsonBackReference
        @ToString.Exclude
        private List<Promo> promos;


        @CreationTimestamp
        @Column(name = "created_at")
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
                Variant variant = (Variant) o;
                return getVariantId() != null && Objects.equals(getVariantId(), variant.getVariantId());
        }

        @Override
        public final int hashCode() {
                return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
        }
}
