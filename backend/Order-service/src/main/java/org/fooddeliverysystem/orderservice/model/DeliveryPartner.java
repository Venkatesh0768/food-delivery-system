package org.fooddeliverysystem.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "delivery_partners")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryPartner {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String phoneNumber;

    private String vehicleType;
    private String status;

    private Double currentLon;
    private Double currentLat;

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
}
