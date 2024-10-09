package com.manica.productscatalogue.ordering.cart;

import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import com.manica.productscatalogue.ordering.CartItemsRequest;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;


@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @PutMapping("/cart-items")
    public ResponseEntity<List<CartItem>> addCartItems(
            @RequestParam(defaultValue = "false") boolean replace,
            @RequestBody @Valid CartItemsRequest request,
            Principal principal
    ) {


        return new ResponseEntity<>(cartService
                .addCartItems( request, replace, principal.getName()), HttpStatus.CREATED);
    }

    @GetMapping("/cart-items")
    public ResponseEntity<Set<CartItem>> findAllCartItems(
            Principal user
    ) {
        return new ResponseEntity<>(cartService.findCartItems(user.getName()), HttpStatus.OK);
    }

    @PutMapping("/cart-items/{id}")
    public ResponseEntity<CartItem> updateCartItem(
            @PathVariable @Parameter(example = "21") long id,
            @RequestBody @Valid CartItemUpdate request,
            Principal user
    ) {
        return ResponseEntity.ok(cartService.updateCartItem(request, id, user.getName()));
    }



    @DeleteMapping ("cart-items/{id}")
    public ResponseEntity<Void> deleteCartItem(
            @PathVariable @Parameter(example = "21") long id,
            Principal user
    ) {
        cartService.deleteCartItemById(id, user.getName());
        return ResponseEntity.noContent().build();
    }


}
