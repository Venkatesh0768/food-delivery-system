package org.fooddeliverysystem.orderservice.model;


import jakarta.persistence.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;
    private UUID userId;
    private UUID restaurantId;
    private Instant createdAt = Instant.now();
}
