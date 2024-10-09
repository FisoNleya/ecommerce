package com.manica.productscatalogue.promos;

import com.manica.productscatalogue.inventory.variant.Variant;
import com.manica.productscatalogue.subscriptions.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "promo", uniqueConstraints = {
        @UniqueConstraint(name = "unique_variant_promo_start",columnNames = {"variant_id","start_date"})
})
public class Promo {


    @Id
    @GeneratedValue
    @Column(name = "promo_id")
    private Long promoId;

    private String promoName;

    @Column(name = "promo_code", nullable = false, unique = true)
    private String promoCode;

    private String promoDescription;

    @Column(name = "start_date" , nullable = false)
    private LocalDate startDate;

    private boolean isAdminApproved;

    private boolean isActive;

    private LocalDate endDate;


    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
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
            nullable = false,
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name = "user_id_fk"))
    private User createdBy;




}
