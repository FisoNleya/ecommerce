package com.manica.productscatalogue.subscriptions.vendor;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "vendor",uniqueConstraints = {
        @UniqueConstraint(name = "unique_vendors",columnNames = {"trade_name","vendor_name"})
})
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {


    @Id
    @Column(name = "vendor_id", unique = true)
    private Long vendorId;

    @Column(name = "vendor_name")
    private String vendorName;

    private boolean enabled ;

    @Column(name = "trade_name")
    private  String tradeName;

    private  String email;

    private   String phoneNumber;
    private  String telephone;
    private  String taxClearance;
    private  String logoUrl;
    private  String addressLine1;
    private  String city;
    private  String addressLine2;
    private  String province;
    private  String country;

}
