package com.manica.productscatalogue.subscriptions.vendor;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Vendor}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorDto implements Serializable {

   private Long vendorId;

   @NotEmpty
   private String vendorName;
   @NotEmpty
   private  String tradeName;
   @NotEmpty
   private  String email;
   @NotEmpty
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