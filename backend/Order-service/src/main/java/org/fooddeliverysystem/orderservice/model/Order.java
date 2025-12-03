package org.fooddeliverysystem.orderservice.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;
    private UUID restaurantId;


    private BigDecimal totalAmount;
    private String paymentMethod;  // COD
    private String orderStatus;    // PLACED, ACCEPTED, DELIVERED

    private Instant createdAt = Instant.now();


}
