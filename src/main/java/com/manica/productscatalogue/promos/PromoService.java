package com.manica.productscatalogue.promos;


import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import com.manica.productscatalogue.exceptions.DataNotFoundException;
import com.manica.productscatalogue.exceptions.DuplicateRecordException;
import com.manica.productscatalogue.inventory.variant.Variant;
import com.manica.productscatalogue.inventory.variant.VariantService;
import com.manica.productscatalogue.subscriptions.user.User;
import com.manica.productscatalogue.subscriptions.user.UserService;
import com.messaging.SystemFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromoService {


    private final PromoRepository promoRepository;
    private final PromoMapper mapper;
    private final UserService userService;
    private final VariantService variantService;

    private static final String PROMO_PREFIX = "PR-";

    public Promo add(PromoRequest request, AuthenticationUser user, Long variantId) {


        Optional<Promo> optionalPromo = promoRepository
                .findByStartDateAndVariant_VariantId(request.startDate(), variantId);

        if (optionalPromo.isPresent()) {
            throw new DuplicateRecordException("Promo already exists for " +" : start date " + request.startDate() + " and variant id " + variantId);
        }

        Promo promo = new Promo();


        User savedUser = userService.findUserByEmail(user.getEmail());
        Variant variant = variantService.findById(variantId);

        mapper.update(request, promo);
        promo.setPromoCode(generatePromoCode());
        promo.setCreatedBy(savedUser);
        promo.setVariant(variant);
        promo = promoRepository.save(promo);

        variantService.addSystemFeatures(List.of(SystemFeature.DISCOUNT_COUPONS.name()), variant);
        return promo;
    }

    public List<Promo> findVariantPromos(long variantId) {
        return promoRepository.findByVariant_VariantId(variantId);
    }



    public Promo findPromoById(Long id) {
        return promoRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Promo " + id + " not found"));
    }

    public void delete(long id) {
        Promo promo = findPromoById(id);
        promoRepository.delete(promo);
    }


    public synchronized String generatePromoCode() {
        return PROMO_PREFIX
                + UUID.randomUUID().toString()
                .replaceAll("-", "");
    }
}
