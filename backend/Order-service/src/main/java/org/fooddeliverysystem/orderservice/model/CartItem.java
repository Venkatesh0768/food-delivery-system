package org.fooddeliverysystem.orderservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cart_items")
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID cartId;
    private UUID menuItemId;

    private String itemName;
    private BigDecimal price;

    private Integer quantity;
}
