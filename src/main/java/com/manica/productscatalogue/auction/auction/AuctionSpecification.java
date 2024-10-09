package com.manica.productscatalogue.auction.auction;

import ch.qos.logback.core.joran.action.ActionUtil;
import com.manica.productscatalogue.inventory.category.Category;
import com.manica.productscatalogue.inventory.product.Product;
import com.manica.productscatalogue.inventory.variant.Variant;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Objects;

public class AuctionSpecification {

    public static Specification<Auction> isUnique() {
        return ((root, query, criteriaBuilder) -> {
            query.distinct(true);
            return null;
        });
    }

    public static Specification<Auction> hasAuctionStatus(AuctionStatus auctionStatus){

        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("auctionStatus"),auctionStatus));
    }
    public static Specification<Auction> hasProductStatus(ProductStatus productStatus){

        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("productStatus"),productStatus));
    }

    public static Specification<Auction> hasQuery(String search) {
            return ((root, query, criteriaBuilder) -> {
                Expression expression = criteriaBuilder.literal("%"+search+"%");
                Join<Auction,Product> auctionProductJoin = root.join("product");
                Join<Product, Category> productCategoryJoin = auctionProductJoin.join("category");
                Predicate productName = criteriaBuilder.like(auctionProductJoin.get("productName"),expression);
                Predicate productCategory = criteriaBuilder.like(productCategoryJoin.get("name"),expression);


                return criteriaBuilder.or(productName,productCategory);
            });


    }

}
