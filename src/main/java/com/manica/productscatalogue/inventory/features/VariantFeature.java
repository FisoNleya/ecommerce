package com.manica.productscatalogue.inventory.features;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.manica.productscatalogue.inventory.variant.Variant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "variant_feature")
@NoArgsConstructor
@AllArgsConstructor
public class VariantFeature {

    @Id
    @GeneratedValue
    @Column(name = "feature_id")
    private Long featureId;


    @Column(name = "feature_name")
    private String name;

    @Column(name = "feature_value")
    private String value;

    @Column(name = "feature_order")
    private Integer order;

    private String other;


    @ManyToOne
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "variant_id",
            nullable = false,
            referencedColumnName = "variant_id",
            foreignKey = @ForeignKey(name = "variant_id_fk"))
    private Variant variant;


}
