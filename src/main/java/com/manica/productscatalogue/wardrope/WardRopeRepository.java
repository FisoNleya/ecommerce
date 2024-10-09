package com.manica.productscatalogue.wardrope;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WardRopeRepository extends JpaRepository<WardRope, Long> {
    Optional<WardRope> findByOwner_Email(String email);
}