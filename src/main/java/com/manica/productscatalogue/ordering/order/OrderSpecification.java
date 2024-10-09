package com.manica.productscatalogue.ordering.order;

import com.manica.productscatalogue.inventory.SearchDTO;
import com.manica.productscatalogue.inventory.category.Category;
import com.manica.productscatalogue.inventory.product.Product;
import com.manica.productscatalogue.inventory.variant.Variant;
import com.manica.productscatalogue.subscriptions.user.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderSpecification {

    Specification<Order> getOrderQuerySpecification(OrderSearchDto searchDTO) {

        Specification<Order> appQuerySpec = null;

        if (Objects.nonNull(searchDTO.getFrom()) || Objects.nonNull(searchDTO.getTo()))
            appQuerySpec = Specification.where(hasDateBetween(searchDTO.getFrom(), searchDTO.getTo()));


        appQuerySpec = Objects.isNull(appQuerySpec) ?
                Specification.where(hasOwner(searchDTO.getOwnerEmail()))
                : appQuerySpec.and(hasOwner(searchDTO.getOwnerEmail()));

        if (Objects.nonNull(searchDTO.getStatus()))
            appQuerySpec = Objects.isNull(appQuerySpec) ?
                    Specification.where(hasStatus(searchDTO.getStatus()))
                    : appQuerySpec.and(hasStatus(searchDTO.getStatus()));


        return appQuerySpec.and(isUnique());

    }


    public static Specification<Order> hasOwner(String owner) {


        return ((root, query, criteriaBuilder) -> {

            Join<Order, User> orderUserJoin = root.join("owner");
            Predicate email = criteriaBuilder.like(criteriaBuilder
                    .lower(orderUserJoin.get("email")), criteriaBuilder.literal("%" + owner + "%"));

            return criteriaBuilder.and(email);

        });

    }

    public static Specification<Order> hasStatus(OrderStatus status) {
        return ((root, query, criteriaBuilder) -> {

            Predicate ratingResult = criteriaBuilder.equal(root.get("orderStatus"), status);
            return criteriaBuilder.and(ratingResult);
        });

    }

    Specification<Order> isUnique() {
        return ((root, query, criteriaBuilder) -> {
            query.distinct(true);
            return null;
        });
    }

    static Specification<Order> hasDateBetween(LocalDateTime startDate, LocalDateTime endDate) {

        if (Objects.nonNull(startDate) && Objects.nonNull(endDate))
            return ((root, query, criteriaBuilder) -> {

                Predicate start = criteriaBuilder.between(root.get("createdAt"), startDate, endDate);
                return criteriaBuilder.and(start);
            });

        if (Objects.nonNull(endDate))
            return ((root, query, criteriaBuilder) -> {

                Predicate lessThan = criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate);
                return criteriaBuilder.and(lessThan);
            });

        else
            return ((root, query, criteriaBuilder) -> {

                Predicate greaterThan = criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate);
                return criteriaBuilder.and(greaterThan);
            });


    }


}
