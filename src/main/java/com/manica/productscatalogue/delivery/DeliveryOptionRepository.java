package com.manica.productscatalogue.delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryOptionRepository extends JpaRepository<DeliveryOption, Long> {

  Optional<DeliveryOption> findByOptionTradeName(String optionTradeName);

}