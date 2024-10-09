package com.manica.productscatalogue.wishlist;


import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import com.manica.productscatalogue.exceptions.DataNotFoundException;
import com.manica.productscatalogue.inventory.variant.Variant;
import com.manica.productscatalogue.inventory.variant.VariantService;
import com.manica.productscatalogue.subscriptions.user.AccessControl;
import com.manica.productscatalogue.subscriptions.user.User;
import com.manica.productscatalogue.subscriptions.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishListService {


    private final UserService userService;
    private final WishListRepository wishListRepository;
    private final VariantService variantService;


    public WishList addToMyWishList(AuthenticationUser authUser, WishListRequest request) {
        User user = userService.findUserByEmail(authUser.getEmail());
        WishList wishList = wishListRepository.findByOwner_Email(authUser.getEmail()).orElseGet(WishList::new);
        wishList.setOwner(user);

        List<Variant> variants = variantService.findAllActiveByIds(request.variantIds());

        List<Variant> existingVariants = wishList.getVariants()==null ? new ArrayList<>(variants) : wishList.getVariants();

        variants.stream().filter(var -> existingVariants.stream()
                .noneMatch(exitVr -> Objects.equals(exitVr.getVariantId(), var.getVariantId())))
                .forEach(existingVariants::add);

        wishList.setVariants(existingVariants);
        return wishListRepository.save(wishList);

    }

    public WishList removeFromMyWishList(AuthenticationUser authUser, WishListRequest request) {

        WishList wishList = wishListRepository.findByOwner_Email(authUser.getEmail())
                .orElseThrow(()-> new DataNotFoundException("User has no wish list"+ authUser.getEmail()));

        List<Variant> variants =  new CopyOnWriteArrayList<>(wishList.getVariants());
        variants.stream().filter(variant -> request.variantIds().stream()
                .anyMatch(existVar -> existVar.equals(variant.getVariantId())))
                .forEach(variants::remove);
        wishList.setVariants(variants);
        return wishListRepository.save(wishList);

    }


    public WishList findMyWishList(AuthenticationUser authUser) {
        User user = userService.findUserByEmail(authUser.getEmail());
        return wishListRepository.findByOwner_Email(authUser.getEmail())
                .orElseGet(() -> wishListRepository.save(WishList.builder()
                .owner(user)
                .variants(Collections.emptyList())
                .build()));

    }


}
