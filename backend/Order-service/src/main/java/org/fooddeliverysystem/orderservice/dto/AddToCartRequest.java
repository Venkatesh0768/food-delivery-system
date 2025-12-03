package org.fooddeliverysystem.orderservice.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToCartRequest {
    private UUID userId;
    private UUID restaurantId;
    private UUID menuItemId;
    private Integer quantity;
}
