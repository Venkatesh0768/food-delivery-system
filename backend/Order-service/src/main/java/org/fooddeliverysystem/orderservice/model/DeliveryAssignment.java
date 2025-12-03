package org.fooddeliverysystem.orderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "delivery_assignments")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryAssignment {
    @Id
    @GeneratedValue
    private UUID id;

    private UUID orderId;
    private UUID partnerId;

    private String status;    // ASSIGNED / PICKED / DELIVERED / CANCELLED

    private Instant assignedAt = Instant.now();
    private Instant pickedAt;
    private Instant deliveredAt;
}
