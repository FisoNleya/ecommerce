package com.manica.productscatalogue.auth.dtos;

public record ValidUser(
         String firstname,
         String lastname,
         String email,
         Role role

) {
}
