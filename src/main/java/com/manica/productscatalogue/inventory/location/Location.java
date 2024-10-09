package com.manica.productscatalogue.inventory.location;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.manica.productscatalogue.subscriptions.user.User;
import com.manica.productscatalogue.subscriptions.vendor.Vendor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "location_type")
    private String type;

    private String addressLine1;
    private String addressLine2;
    private String streetAddress;
    private String suburb;
    private String city;
    private String zone;
    private String zipCode;
    private String province;
    private String country;

    private String company;


    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id",
            nullable = true,
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name = "user_id_fk"))
    private User user;

    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "vendor_id",
            nullable = true,
            referencedColumnName = "vendor_id",
            foreignKey = @ForeignKey(name = "vendor_id_fk"))
    private Vendor vendor;


}
