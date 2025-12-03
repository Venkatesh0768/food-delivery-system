package org.fooddeliverysystem.authservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    private String addressLine1;
    private String addressLine2;

    private String city;
    private String state;
    private String postalCode;
    private String country;

    private Double latitude;
    private Double longitude;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now;
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }


}
