package com.manica.productscatalogue.ordering.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order, String> , JpaSpecificationExecutor<Order> {


    Page<Order> findAllByOrderStatusAndOwner_Email(OrderStatus status, String owner, Pageable pageable);

}