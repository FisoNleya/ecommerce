package com.manica.productscatalogue.ordering.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Set<CartItem> findByCart_CartId(long cartId);

    void deleteAllByCart_CartId(long carId);

    Optional<CartItem> findByItemIdAndCart_Owner(long itemId, String owner);

}