package com.manica.productscatalogue.inventory.variant;


import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import com.manica.productscatalogue.exceptions.*;
import com.manica.productscatalogue.inventory.ImageRepository;
import com.manica.productscatalogue.inventory.SearchDTO;
import com.manica.productscatalogue.inventory.features.VariantFeature;
import com.manica.productscatalogue.inventory.features.VariantFeatureRepository;
import com.manica.productscatalogue.inventory.location.Location;
import com.manica.productscatalogue.inventory.location.LocationService;
import com.manica.productscatalogue.inventory.product.Product;
import com.manica.productscatalogue.inventory.product.ProductService;
import com.manica.productscatalogue.promos.PromoRepository;
import com.manica.productscatalogue.subscriptions.user.AccessControl;
import com.manica.productscatalogue.subscriptions.user.Feature;
import com.manica.productscatalogue.subscriptions.user.User;
import com.manica.productscatalogue.subscriptions.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VariantService {


    private final VariantRepository variantRepository;
    private final VariantSpecification variantSpecification;
    private final ProductService productService;
    private final VariantMapper mapper;
    private final ImageRepository imageRepository;
    private final UserService userService;
    private final PromoRepository promoRepository;
    private final VariantFeatureRepository variantFeatureRepository;
    private final LocationService locationService;

    public Variant add(VariantRequest request) {

        Optional<Variant> optionalVariant = variantRepository.findBySizeAndColorHexAndProduct_ProductId(
                request.size(), request.colorHex(), request.productId());

        if (optionalVariant.isPresent())
            throw new DuplicateRecordException("Variant already exist with sku : " + request.variantSku());


        Variant variant = new Variant();
        mapper.update(request, variant);

        Location location = locationService.findById(request.locationId());
        Product product = productService.findById(request.productId());

        variant.setProduct(product);
        variant.setActive(true);
        variant.setLocation(location);

        variant = variantRepository.save(variant);
        productService.update(variant);
        return variant;
    }


    public Page<Variant> findAllBy(SearchDTO searchDTO,
                                   Pageable pageable) {


        Specification<Variant> appQuery = variantSpecification.getVarinatQuerySpecification(searchDTO, null);
        return map(variantRepository.findAll(appQuery, pageable));

    }


    public Page<Variant> findAllFeaturedVariantsBy(SearchDTO searchDTO, AuthenticationUser authUser,
                                                   Pageable pageable) {

        User user = userService.findUserByEmail(authUser.getEmail());
        List<String> userFeatures = user.getFeatures().stream().map(ft -> ft.getSystemFeature().name()).toList();

        Specification<Variant> appQuery = variantSpecification.getVarinatQuerySpecification(searchDTO, userFeatures);
        return map(variantRepository.findAll(appQuery, pageable));

    }



    public Page<Variant> findAllByVendor(long vendorId, Boolean active, Pageable pageable, AuthenticationUser user) {


        User savedUser = userService.findUserByEmail(user.getEmail());

        //If user is admin, or has rights in that vendor show him all vendor products
        if (AccessControl.hasRightsInVendor(vendorId, user, savedUser))
            return variantRepository.findAllByProduct_Vendor_VendorId(vendorId, pageable);

        if (Objects.isNull(active))
                return map(variantRepository.findAllByProduct_Vendor_VendorIdAndActiveAndAdminApproved(vendorId, true, true, pageable));

        return map(variantRepository.findAllByProduct_Vendor_VendorIdAndAdminApproved(vendorId, true, pageable));

    }


    public Page<Variant> map(Page<Variant> variants) {
        return variants.map(
                variant -> {
                    variant.setImages(imageRepository.findByVariant_VariantId(variant.getVariantId()));
                    variant.setPromos(promoRepository.findByVariant_VariantId(variant.getVariantId()));
                    return variant;
                });
    }

    public Page<Variant> findRelatedVariants(long variantId, Pageable pageable) {

        Variant variant = findById(variantId);
        return variantRepository.findAllByProduct_ProductIdAndActiveAndVariantIdNotAndAdminApproved(
                variant.getProduct().getProductId(), true, variantId, true, pageable);
    }


    public List<Variant> getProductVariants(long productId) {
        return variantRepository.findByProduct_ProductId(productId);
    }

    public Variant update(long variantId, VariantUpdate request, AuthenticationUser user) {


        if (Objects.nonNull(request.adminApproved()) && (!AccessControl.hasAdminUpdate(user)))
            throw new UnAuthorisedException("User does not have right to complete this action : " + user.getEmail());


        Variant variant = findById(variantId);
        mapper.update(request, variant);
        return variantRepository.save(variant);
    }


    public Variant findById(long variantId) {
        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new DataNotFoundException("Variant not found with id : " + variantId));
        variant.setImages(imageRepository.findByVariant_VariantId(variantId));
        variant.setPromos(promoRepository.findByVariant_VariantId(variantId));
        variant.setVariantFeatures(variantFeatureRepository.findByVariant_VariantId(variantId));
        return variant;
    }


    public Variant findBySize(long productId, String size) {

        Variant variant = variantRepository.findFirstBySizeAndProduct_ProductId(size, productId)
                .orElse(null);

        if (variant == null)
            return null;
        variant.setImages(imageRepository.findByVariant_VariantId(variant.getVariantId()));
        return variant;
    }


    public Variant findByColor(long variantId, String color, String hexValue) {

        Variant variant = variantRepository
                .findFirstByColorAndVariantIdNotOrColorHexAndVariantIdNot(color, variantId, hexValue, variantId)
                .orElse(null);

        if (variant == null)
            return null;
        variant.setImages(imageRepository.findByVariant_VariantId(variantId));
        return variant;
    }

    public List<Variant> findAllByIds(List<Long> variantIds) {
        return variantRepository.findByVariantIdIn(variantIds);
    }
    public List<Variant> findAllActiveByIds(List<Long> variantIds) {
        return variantRepository.findByVariantIdInAndActive(variantIds, true);
    }

//    public Variant vendorFindById(long variantId, long vendorId) {
//
//        Variant variant = variantRepository.findByVariantIdAndProduct_Vendor_VendorId(variantId, vendorId)
//                .orElseThrow(() -> new DataNotFoundException("Variant not found with id : " + variantId + " for vendor : " + vendorId));
//        variant.setImages(imageRepository.findByVariant_VariantId(variantId));
//        return variant;
//    }
//
//    public Variant ownerFindById(long variantId, String ownerEmail) {
//        Variant variant = variantRepository.findByVariantIdAndProduct_Owner_Email(variantId, ownerEmail)
//                .orElseThrow(() -> new DataNotFoundException("Variant not found with id : " + variantId + " for owner : " + ownerEmail));
//        variant.setImages(imageRepository.findByVariant_VariantId(variantId));
//        return variant;
//    }


    public Variant findByVendorOrOwner(long variantId, AuthenticationUser authUser, User user) {

        Variant variant = findById(variantId);
        Product product = variant.getProduct();

        if (AccessControl.hasAdminUpdate(authUser))
            return variant;
        else if (product.getOwner() != null && product.getOwner().getEmail().equals(user.getEmail()))
            return variant;
        else if (product.getVendor() != null && user.getUserRights().stream().anyMatch(rt -> Objects.equals(product.getVendor().getVendorId(), rt.getVendor().getVendorId()))) {
            return variant;
        }
        throw new UnAuthorisedException("User does not have right to complete this action : " + user.getEmail());
    }


    public void deleteById(long variantId, AuthenticationUser authUser) {

        User user = userService.findUserByEmail(authUser.getEmail());
        Variant variant = findByVendorOrOwner(variantId, authUser, user);

        variantRepository.delete(variant);
    }


    public List<Variant> verifyVariants(long productId, VariantVerification request) {

        if (Objects.nonNull(request.verifyAllProductVariants())) {

            List<Variant> variants = variantRepository.findByProduct_ProductId(productId);
            variants.forEach(variant -> variant.setAdminApproved(true));
            return variantRepository.saveAll(variants);
        } else if (Objects.nonNull(request.variantIds())) {

            List<Variant> variants = variantRepository.findByVariantIdIn(request.variantIds());
            variants.forEach(variant -> variant.setAdminApproved(true));
            return variantRepository.saveAll(variants);

        } else
            throw new InvalidRequestException("Add variant or select verify all variants");


    }


    public Variant addSystemFeatures(List<String> features, Variant variant) {

        List<String> savedFeatures = variant.getSystemFeatures() == null ?
                new ArrayList<>() : variant.getSystemFeatures();
        savedFeatures.addAll(features);
        variant.setSystemFeatures(savedFeatures);

        return variantRepository.save(variant);
    }

    public void activateAll() {
        List<Variant> variants = variantRepository.findAll();
        variants.stream().unordered().parallel().forEach(var -> {
            var.setActive(true);
            var.setAdminApproved(true);
            var.setHidden(false);
        });
        variantRepository.saveAll(variants);
    }


}
