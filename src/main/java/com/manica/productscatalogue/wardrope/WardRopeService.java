package com.manica.productscatalogue.wardrope;

import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import com.manica.productscatalogue.exceptions.DataNotFoundException;
import com.manica.productscatalogue.inventory.variant.Variant;
import com.manica.productscatalogue.inventory.variant.VariantService;
import com.manica.productscatalogue.subscriptions.user.User;
import com.manica.productscatalogue.subscriptions.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@Slf4j
@Service
@RequiredArgsConstructor
public class WardRopeService {


    private final UserService userService;
    private final WardRopeRepository wardRopeRepository;
    private final VariantService variantService;


    public WardRope addToMyWardRope(AuthenticationUser authUser, WardRopeRequest request) {
        User user = userService.findUserByEmail(authUser.getEmail());
        WardRope wardRope = wardRopeRepository.findByOwner_Email(authUser.getEmail()).orElseGet(WardRope::new);
        wardRope.setOwner(user);

        List<Variant> variants = variantService.findAllByIds(request.variantIds());
        wardRope.setVariants(variants);
        return wardRopeRepository.save(wardRope);

    }

    public WardRope removeFromMyWardRope(AuthenticationUser authUser, WardRopeRequest request) {

        WardRope wardRope = wardRopeRepository.findByOwner_Email(authUser.getEmail())
                .orElseThrow(()-> new DataNotFoundException("User has no wardrope"+ authUser.getEmail()));

        List<Variant> variants =  new CopyOnWriteArrayList<>(wardRope.getVariants());
        variants.stream().filter(variant -> request.variantIds().stream()
                        .anyMatch(existVar -> existVar.equals(variant.getVariantId())))
                .forEach(variants::remove);
        wardRope.setVariants(variants);
        return wardRopeRepository.save(wardRope);

    }


    public WardRope findMyWardRope(AuthenticationUser authUser) {
        User user = userService.findUserByEmail(authUser.getEmail());
        return wardRopeRepository.findByOwner_Email(authUser.getEmail())
                .orElseGet(() -> wardRopeRepository.save(WardRope.builder()
                        .owner(user)
                        .variants(Collections.emptyList())
                        .build()));

    }
    
}
