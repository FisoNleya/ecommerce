package com.manica.productscatalogue.inventory.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.manica.productscatalogue.auction.auction.Auction;
import com.manica.productscatalogue.inventory.brand.Brand;
import com.manica.productscatalogue.inventory.category.Category;
import com.manica.productscatalogue.subscriptions.user.User;
import com.manica.productscatalogue.subscriptions.vendor.Vendor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product",uniqueConstraints = {
        @UniqueConstraint(name = "unique_product_&_brand",columnNames = {"product_name","brand_id"})
})
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    private String description;

    @Column
    private String sku;

    private BigDecimal regularPrice;

    private int quantity;

    @Column(columnDefinition = "TEXT")
    private String extendedDescription;



    @ElementCollection(targetClass=String.class, fetch=FetchType.EAGER)
    private Set<String> colors;

    @ElementCollection(targetClass=String.class, fetch=FetchType.EAGER)
    private Set<String> sizes;


    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "brand_id",
            nullable = false,
            referencedColumnName = "brand_id",
            foreignKey = @ForeignKey(name = "brand_id_fk"))
    private Brand brand;


    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "category_id",
            nullable = true,
            referencedColumnName = "category_id",
            foreignKey = @ForeignKey(name = "category_id_fk"))
    private Category category;


    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "vendor_id",
            nullable = true,
            referencedColumnName = "vendor_id",
            foreignKey = @ForeignKey(name = "vendor_id_fk"))
    private Vendor vendor;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id",
            nullable = true,
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name = "user_id_fk"))
    private User owner;



    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @CreatedBy
    private String createdBy;
}
