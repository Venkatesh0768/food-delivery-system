package org.fooddeliverysystem.restaurantservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID restaurantId;
    private UUID menuId;

    private String name;
    private String description;
    private BigDecimal price;
    private Boolean veg;
    private Boolean isAvailable = true;
    private String imageUrl;

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

}
