package com.messaging;

import com.manica.productscatalogue.subscriptions.vendor.VendorDto;

import java.io.Serializable;
import java.time.LocalDateTime;


public record UserRightDto(Long userRightId, Permission permission, VendorDto vendor, LocalDateTime createdAt,
                           String grantedBy) implements Serializable {
}