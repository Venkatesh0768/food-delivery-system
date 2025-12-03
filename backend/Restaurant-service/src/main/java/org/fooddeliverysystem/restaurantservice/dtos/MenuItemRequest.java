package org.fooddeliverysystem.restaurantservice.dtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Service
public class MenuItemRequest {
    private UUID restaurantId;
    private UUID menuId;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean veg;
    private String imageUrl;
}
