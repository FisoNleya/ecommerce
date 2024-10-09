package com.manica.productscatalogue.wishlist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    Optional<WishList> findByOwner_Email(String email);

}