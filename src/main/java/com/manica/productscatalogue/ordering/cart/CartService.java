package com.manica.productscatalogue.ordering.cart;


import com.manica.productscatalogue.exceptions.DataNotFoundException;
import com.manica.productscatalogue.exceptions.InvalidRequestException;
import com.manica.productscatalogue.inventory.variant.Variant;
import com.manica.productscatalogue.inventory.variant.VariantService;
import com.manica.productscatalogue.ordering.CartItemsRequest;
import com.manica.productscatalogue.ordering.order.Order;
import com.manica.productscatalogue.subscriptions.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final VariantService variantService;

    private final CartItemRepository cartItemRepository;


    public List<CartItem> addCartItems(CartItemsRequest request, boolean replace, String userEmail) {

        List<CartItemRequest> itemsToAdd  = request.cartItems();

        Cart cart = cartRepository.findByOwnerAndActive(userEmail, true)
                .orElseGet(()->cartRepository.save(Cart.builder()
                        .owner(userEmail)
                        .active(true)
                        .totalPrice(BigDecimal.ZERO)
                        .build()));


        Set<CartItem> existingCartItems = cartItemRepository.findByCart_CartId(cart.getCartId());

        if (replace) {
            cartItemRepository.deleteAll(existingCartItems);
            existingCartItems.clear();
        }

        Set<CartItem> newItems = itemsToAdd.stream().map(item -> {

            //IF its already in the list , look for the item and increment quantity instead
            // Cart Item total price  = val * quantity

            Variant variant = variantService.findById(item.variantId());

            //Check product quantity
            if(variant.getInventoryQuantity() < 1){
                throw new InvalidRequestException("Variant quantity depleted , item cant be added to the cart, with Id :"+variant.getVariantId());
            }

            //Make sure the products have specific location specified
            if(Objects.isNull(variant.getLocation()))
                throw new InvalidRequestException("Please update variant and specify location for variant : "+ variant.getVariantId());

            CartItem cartItem = CartItem.builder()
                    .variant(variant)
                    .quantity(item.quantity())
                    .price(calculateTotalPrice(variant.getPrice(), item.quantity()))
                    .unitPrice(variant.getPrice())
                    .createdBy(userEmail)
                    .cart(cart)
                    .build();

            Optional<CartItem> optionalCartItem = existingCartItems.stream()
                    .filter(searchItem -> Objects.equals(searchItem.getVariant()
                            .getVariantId(), variant.getVariantId()))
                    .findFirst();

            if (optionalCartItem.isPresent()) {


                CartItem existingCartItem = optionalCartItem.get();
                long quantity = item.quantity() + existingCartItem.getQuantity();


                cartItem = existingCartItem;
                cartItem.setPrice(calculateTotalPrice(variant.getPrice(), quantity));
                cartItem.setQuantity(quantity);

            }

            return cartItem;

        }).collect(Collectors.toSet());

        List<CartItem> savedItems = cartItemRepository.saveAll(newItems);
        updateCartTotal(cart, savedItems);
        return savedItems;
    }


    public Set<CartItem> findCartItems(String userId) {

        Cart cart = cartRepository.findByOwnerAndActive(userId, true)
                .orElseGet(()->cartRepository.save(Cart.builder()
                        .owner(userId)
                        .active(true)
                        .totalPrice(BigDecimal.ZERO)
                        .build()));

        return cartItemRepository.findByCart_CartId(cart.getCartId());
    }


    public CartItem updateCartItem(CartItemUpdate request, long itemId, String owner) {

        CartItem cartItem = findCartItemById(itemId, owner);
        cartItem.setQuantity(request.quantity());
        cartItem.setPrice(calculateTotalPrice(cartItem.getPrice(), request.quantity()));
        cartItem = cartItemRepository.save(cartItem);
        updateCartTotal(cartItem.getCart());
        return cartItem;
    }


    private CartItem findCartItemById(long itemId, String owner) {
        return cartItemRepository.findByItemIdAndCart_Owner(itemId, owner)
                .orElseThrow(() -> new DataNotFoundException("Cart Item not found with id : " + itemId));
    }


    public void deleteCartItemById(long itemId, String owner) {
        CartItem item = findCartItemById(itemId, owner);
        cartItemRepository.deleteById(itemId);
        updateCartTotal(item.getCart());
    }


    private BigDecimal calculateTotalPrice(BigDecimal price, long quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    private void updateCartTotal(Cart cart, List<CartItem> cartItems) {

        BigDecimal total = cartItems.stream()
                .reduce(BigDecimal.ZERO, (val, item) -> val.add(item.getPrice()), BigDecimal::add);
        cart.setTotalPrice(total);
        cartRepository.save(cart);
    }

    private void updateCartTotal(Cart cart) {

        Set<CartItem> cartItems = cartItemRepository.findByCart_CartId(cart.getCartId());

        BigDecimal total = cartItems.stream()
                .reduce(BigDecimal.ZERO, (val, item) -> val.add(item.getPrice()), BigDecimal::add);
        cart.setTotalPrice(total);
        cartRepository.save(cart);
    }


    @Async
    @EventListener
    public void onOrderDeActivateCart(Order order){
        User user  = order.getOwner();
         Optional<Cart> optionalCart = cartRepository.findByOwnerAndActive(user.getEmail(), true);
         if(optionalCart.isPresent()){
             Cart cart = optionalCart.get();
             cart.setActive(false);
             cartRepository.save(cart);
             log.info("Cleared cart id : {} for user : {} ", cart.getCartId(), user.getEmail());
         }

    }


}
