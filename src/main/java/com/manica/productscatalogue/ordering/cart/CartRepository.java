package com.manica.productscatalogue.ordering.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByOwnerAndActive(String owner,boolean active);

}