package com.manica.productscatalogue.inventory.variant;

import com.manica.productscatalogue.inventory.SearchDTO;
import com.manica.productscatalogue.inventory.brand.Brand;
import com.manica.productscatalogue.inventory.category.Category;
import com.manica.productscatalogue.inventory.product.Product;
import com.manica.productscatalogue.inventory.rating.Rating;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class VariantSpecification {

    public Specification<Variant> getVarinatQuerySpecification(SearchDTO searchDTO, List<String> features) {

        Specification<Variant> appQuerySpec = null;

        if (Objects.nonNull(searchDTO.getMaxPrice()) || Objects.nonNull(searchDTO.getMinPrice()))
            appQuerySpec = Specification.where(hasPrice(searchDTO.getMinPrice(), searchDTO.getMaxPrice()));

        if (Objects.nonNull(searchDTO.getCategories()))
            appQuerySpec = Objects.isNull(appQuerySpec) ?
                    Specification.where(hasCategories(searchDTO.getCategories()))
                    : appQuerySpec.and(hasCategories(searchDTO.getCategories()));

        if (Objects.nonNull(searchDTO.getBrands()))
            appQuerySpec = Objects.isNull(appQuerySpec) ?
                    Specification.where(hasBrands(searchDTO.getBrands()))
                    : appQuerySpec.and(hasBrands(searchDTO.getBrands()));


        if (Objects.nonNull(searchDTO.getSearch()))
            appQuerySpec = Objects.isNull(appQuerySpec) ?
                    Specification.where(hasSearch(searchDTO.getSearch()))
                    : appQuerySpec.and(hasSearch(searchDTO.getSearch()));

        return appQuerySpec.and(isUnique()).and(active()).and(adminApproved())
                .and(features == null? hasNoSpecial() : hasAnyFeature(features));

    }



    public static Specification<Variant> hasPrice(BigDecimal minPrice, BigDecimal maxPrice) {

        if (Objects.nonNull(minPrice) && Objects.nonNull(maxPrice))
            return ((root, query, criteriaBuilder) -> {


                Predicate start = criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
                return criteriaBuilder.and(start);
            });

        if (Objects.nonNull(maxPrice))
            return ((root, query, criteriaBuilder) -> {

                Predicate lessThan = criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
                return criteriaBuilder.and(lessThan);
            });

        else
            return ((root, query, criteriaBuilder) -> {

                Predicate greaterThan = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
                return criteriaBuilder.and(greaterThan);
            });


    }


    public static Specification<Variant> hasCategory(String category) {


        return ((root, query, criteriaBuilder) -> {

            Join<Variant, Product> varinatProductJoin = root.join("product");
            Join<Product, Category> productCategoryJoin = varinatProductJoin.join("category");
            Predicate categoryName = criteriaBuilder.like(criteriaBuilder
                    .lower(productCategoryJoin.get("name")), criteriaBuilder.literal("%" + category + "%"));

            return criteriaBuilder.and(categoryName);

        });

    }


    public static Specification<Variant> active() {
        return ((root, query, criteriaBuilder) -> {

            Predicate ratingResult = criteriaBuilder.equal(root.get("active"), true);
            return criteriaBuilder.and(ratingResult);
        });

    }

    public static Specification<Variant> adminApproved() {
        return ((root, query, criteriaBuilder) -> {

            Predicate ratingResult = criteriaBuilder.equal(root.get("adminApproved"), true);
            return criteriaBuilder.and(ratingResult);
        });

    }

    public static Specification<Variant> hidden() {
        return ((root, query, criteriaBuilder) -> {

            Predicate ratingResult = criteriaBuilder.equal(root.get("hidden"), false);
            return criteriaBuilder.and(ratingResult);
        });

    }



    public static Specification<Variant> hasAnyFeature(List<String> features) {

        return ((root, query, criteriaBuilder) -> {
            Predicate featuresPredicate = root.join("systemFeatures").in(features);
            return criteriaBuilder.and(featuresPredicate);
        });

    }


    public static Specification<Variant> hasNoSpecial() {


        return ((root, query, criteriaBuilder) -> {
            Predicate featuresIsEmpty = criteriaBuilder.isEmpty(root.get("systemFeatures"));
            return criteriaBuilder.and(featuresIsEmpty);
        });

    }


    public static Specification<Variant> hasRating(Integer rating) {


        return ((root, query, criteriaBuilder) -> {

            Join<Variant, Rating> variantRatingJoin = root.join("ratings");

            Predicate ratingResult = criteriaBuilder.equal(variantRatingJoin.get("value"), rating);
            return criteriaBuilder.and(ratingResult);
        });

    }



    public static Specification<Variant> hasCategories(List<String> categories) {
        return ((root, query, criteriaBuilder) -> {

            Join<Variant, Product> variantProductJoin = root.join("product");
            Join<Product, Category> productCategoryJoin = variantProductJoin.join("category");

            Predicate categoryName = criteriaBuilder.in(productCategoryJoin.get("name")).value(categories);
            return criteriaBuilder.and(categoryName);
        });
    }

    public static Specification<Variant> hasBrands(List<String> brands) {
        return ((root, query, criteriaBuilder) -> {

            Join<Variant, Product> variantProductJoin = root.join("product");

            Join<Product, Brand> productBrandJoin = variantProductJoin.join("brand");

            Predicate categoryName = criteriaBuilder.in(productBrandJoin.get("brandName")).value(brands);
            return criteriaBuilder.and(categoryName);
        });
    }


    public Specification<Variant> isUnique() {
        return ((root, query, criteriaBuilder) -> {
            query.distinct(true);
            return null;
        });
    }

    public Specification<Variant> hasSearch(String search) {
        return ((root, query, criteriaBuilder) -> {

            String cleanSearchQuery = search.toLowerCase().trim();

            Predicate variantName = criteriaBuilder.like(root.get("variantName"), criteriaBuilder.literal("%" + cleanSearchQuery + "%"));

            Join<Variant, Product> varinatProductJoin = root.join("product");
            Predicate productName = criteriaBuilder.like(criteriaBuilder.lower(varinatProductJoin.get("productName")), criteriaBuilder.literal("%" + cleanSearchQuery + "%"));

            Predicate variantSku = criteriaBuilder.like(root.get("variantSku"), criteriaBuilder.literal("%" + cleanSearchQuery + "%"));

            Predicate description = criteriaBuilder.like(root.get("color"), criteriaBuilder.literal("%" + cleanSearchQuery + "%"));


            Join<Product, Brand> productBrandJoin = varinatProductJoin.join("brand");
            Predicate brandName = criteriaBuilder.like(criteriaBuilder.lower(productBrandJoin.get("brandName")), criteriaBuilder.literal("%" + cleanSearchQuery + "%"));

            Join<Product, Category> productCategoryJoin = varinatProductJoin.join("category");
            Predicate categoryName = criteriaBuilder.like(criteriaBuilder.lower(productCategoryJoin.get("name")), criteriaBuilder.literal("%" + cleanSearchQuery + "%"));

            Join<Product, Category> productVendorJoin = varinatProductJoin.join("vendor");
            Predicate vendorName = criteriaBuilder.like(criteriaBuilder.lower(productVendorJoin.get("vendorName")), criteriaBuilder.literal("%" + cleanSearchQuery + "%"));


            return criteriaBuilder.or(variantName, productName, variantSku, description, brandName, categoryName, vendorName);

        });
    }


}
