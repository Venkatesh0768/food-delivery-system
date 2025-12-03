package org.fooddeliverysystem.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.fooddeliverysystem.orderservice.model.Cart;
import org.fooddeliverysystem.orderservice.model.CartItem;
import org.fooddeliverysystem.orderservice.model.Order;
import org.fooddeliverysystem.orderservice.model.OrderItem;
import org.fooddeliverysystem.orderservice.repository.CartItemRepository;
import org.fooddeliverysystem.orderservice.repository.CartRepository;
import org.fooddeliverysystem.orderservice.repository.OrderItemRepository;
import org.fooddeliverysystem.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public Order createOrder(UUID id){
        Cart cart =  cartRepository.findByUserId(id).orElseThrow(
                ()-> new RuntimeException("The Cart is Empty  add Some Items")
        );

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
        BigDecimal totalAmount = items.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .userId(id)
                .restaurantId(cart.getRestaurantId())
                .totalAmount(totalAmount)
                .paymentMethod("COD")
                .orderStatus("Placed")
                .build();

        Order saved = orderRepository.save(order);

        for (CartItem  item : items){
            OrderItem orderItem = OrderItem.builder()
                    .orderId(saved.getId())
                    .menuItemId(item.getMenuItemId())
                    .itemName(item.getItemName())
                    .price(item.getPrice())
                    .quantity(item.getQuantity())
                    .build();
            orderItemRepository.save(orderItem);
        }

        return saved;
    }


}
