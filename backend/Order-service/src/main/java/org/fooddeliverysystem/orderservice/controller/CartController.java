package org.fooddeliverysystem.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.fooddeliverysystem.orderservice.dto.AddToCartRequest;
import org.fooddeliverysystem.orderservice.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequest req) {
        return ResponseEntity.ok(cartService.addToCart(req));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCart(@PathVariable UUID userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }
}
