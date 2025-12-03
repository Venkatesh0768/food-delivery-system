package org.fooddeliverysystem.restaurantservice.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID ownerId;

    private String name;
    private String description;
    private Boolean pureVeg;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String pincode;

    private Double latitude;
    private Double longitude;

    private Boolean active = true;

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
}
