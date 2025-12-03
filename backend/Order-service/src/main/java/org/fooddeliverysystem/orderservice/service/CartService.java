package org.fooddeliverysystem.orderservice.service;


import lombok.RequiredArgsConstructor;
import org.fooddeliverysystem.orderservice.client.MenuClient;
import org.fooddeliverysystem.orderservice.dto.AddToCartRequest;
import org.fooddeliverysystem.orderservice.dto.MenuItemResponse;
import org.fooddeliverysystem.orderservice.model.Cart;
import org.fooddeliverysystem.orderservice.model.CartItem;
import org.fooddeliverysystem.orderservice.repository.CartItemRepository;
import org.fooddeliverysystem.orderservice.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MenuClient menuClient;


    public CartItem addToCart(AddToCartRequest request) {
        //step 1 check if user already has a cart
        Cart cart = cartRepository.findByUserId(request.getUserId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(request.getUserId());
                    return cartRepository.save(newCart);
                });

        MenuItemResponse menuItem = menuClient.getMenuItem(request.getMenuItemId());

        CartItem item = new CartItem();
        item.setCartId(cart.getId());
        item.setMenuItemId(request.getMenuItemId());
        item.setItemName(menuItem.getName());
        item.setQuantity(request.getQuantity());
        item.setPrice(menuItem.getPrice());

        return  cartItemRepository.save(item);
    }

    public List<CartItem> getCartItems(UUID userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        return cartItemRepository.findByCartId(cart.getId());
    }

}
