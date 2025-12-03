package org.fooddeliverysystem.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.fooddeliverysystem.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createOrder(@PathVariable UUID userId){
        return ResponseEntity.ok(orderService.createOrder(userId));
    }

}
