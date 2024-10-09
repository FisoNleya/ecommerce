package com.manica.productscatalogue.auth.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {


    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete"),


    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_CREATE("user:create"),
    USER_DELETE("user:delete"),


    PRODUCT_CREATE("product:create"),
    PRODUCT_UPDATE("product:update"),
    PRODUCT_DELETE("product:delete"),
    PRODUCT_READ("product:read"),

    VENDOR_UPDATE("vendor:update"),
    VENDOR_READ("vendor:read"),

    COUPON_CREATE("coupon:create"),
    COUPON_UPDATE("coupon:update"),
    COUPON_READ("coupon:read"),
    COUPON_DELETE("coupon:delete"),


    VENDOR_ADMIN_CREATE("vendor_admin:create"),
    VENDOR_ADMIN_DELETE("vendor_admin:create");


    @Getter
    private final String permissionValue;
}
