package com.manica.productscatalogue.inventory.product;

import com.manica.productscatalogue.inventory.SearchDTO;
import com.manica.productscatalogue.inventory.brand.Brand;
import com.manica.productscatalogue.inventory.category.Category;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSpecification {

    public Specification<Product> getAppQuerySpecification(SearchDTO searchDTO) {

        Specification<Product> appQuerySpec = null;
//        if (Objects.nonNull(searchDTO.getApplicantName()))
//            appQuerySpec = Specification.where(hasApplicantName(searchDTO.getApplicantName()));

//        if (Objects.nonNull(searchDTO.getProductStatus()))
//            appQuerySpec = Objects.isNull(appQuerySpec) ?
//                    Specification.where(hasStatus(searchDTO.getProductStatus()))
//                    : appQuerySpec.and(hasStatus(searchDTO.getProductStatus()));

        if (Objects.nonNull(searchDTO.getSearch()))
            appQuerySpec = Objects.isNull(appQuerySpec) ?
                    Specification.where(hasSearch(searchDTO.getSearch()))
                    : appQuerySpec.and(hasSearch(searchDTO.getSearch()));

        return appQuerySpec.and(isUnique());

    }

    public Specification<Product> isUnique() {
        return ((root, query, criteriaBuilder) -> {
            query.distinct(true);
            return null;
        });
    }

    public Specification<Product> hasSearch(String search) {
        return ((root, query, criteriaBuilder) -> {

            String cleanSearchQuery = search.toLowerCase().trim();

            Predicate productName = criteriaBuilder.like(root.get("productName"), criteriaBuilder.literal("%" + cleanSearchQuery + "%"));
            Predicate sku = criteriaBuilder.like(root.get("sku"), criteriaBuilder.literal("%" + cleanSearchQuery + "%"));
            Predicate description = criteriaBuilder.like(root.get("description"), criteriaBuilder.literal("%" + cleanSearchQuery + "%"));

            Join<Product, Brand> productBrandJoin = root.join("brand");
            Predicate brandName = criteriaBuilder.like(criteriaBuilder.lower(productBrandJoin.get("brandName")), criteriaBuilder.literal("%" + cleanSearchQuery + "%"));

            Join<Product, Category> productCategoryJoin = root.join("category");
            Predicate categoryName = criteriaBuilder.like(criteriaBuilder.lower(productCategoryJoin.get("name")), criteriaBuilder.literal("%" + cleanSearchQuery + "%"));

            Join<Product, Category> productVendorJoin = root.join("vendor");
            Predicate vendorName = criteriaBuilder.like(criteriaBuilder.lower(productVendorJoin.get("vendorName")), criteriaBuilder.literal("%" + cleanSearchQuery + "%"));


            return criteriaBuilder.or(productName, sku, description, brandName, categoryName, vendorName);

        });
    }


}
