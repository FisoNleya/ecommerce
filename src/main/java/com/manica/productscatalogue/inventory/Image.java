package com.manica.productscatalogue.inventory;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.manica.productscatalogue.inventory.variant.Variant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image",uniqueConstraints = {
        @UniqueConstraint(name = "unique_image_order",columnNames = {"variant_id","image_order"})
})
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "image_order")
    private int order;


    @ManyToOne
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "variant_id",
            nullable = false,
            referencedColumnName = "variant_id",
            foreignKey = @ForeignKey(name = "variant_id_fk"))
    private Variant variant;

}
