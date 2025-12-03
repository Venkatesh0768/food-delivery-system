package org.fooddeliverysystem.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItemResponse {
    private UUID id;
    private UUID restaurantId;
    private UUID menuId;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean veg;
    private Boolean isAvailable;
    private String imageUrl;
    private Instant createdAt;
    private Instant updatedAt;
}
