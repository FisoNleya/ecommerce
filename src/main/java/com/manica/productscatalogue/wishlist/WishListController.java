package com.manica.productscatalogue.wishlist;


import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import com.manica.productscatalogue.inventory.rating.Rating;
import com.manica.productscatalogue.inventory.rating.RatingRequest;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/api/v1/wish-list")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    @PutMapping("mine/variants")
    public ResponseEntity<WishList> add(
            Principal principal,
            @RequestBody @Valid WishListRequest request
    ) {

        var user = (AuthenticationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return new ResponseEntity<>(wishListService.addToMyWishList(user, request), HttpStatus.OK);
    }


    @DeleteMapping("mine/variants")
    public ResponseEntity<WishList> remove(
            Principal principal,
            @RequestBody @Valid WishListRequest request
    ) {

        var user = (AuthenticationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return new ResponseEntity<>(wishListService.removeFromMyWishList(user, request), HttpStatus.OK);
    }


    @GetMapping("mine")
    public ResponseEntity<WishList> getMyWishList(
            Principal principal
    ) {

        var user = (AuthenticationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return new ResponseEntity<>(wishListService.findMyWishList(user), HttpStatus.OK);
    }



}
