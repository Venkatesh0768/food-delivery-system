package org.fooddeliverysystem.restaurantservice.dtos;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RestaurantRequest {
    private UUID ownerId;
    private String name;
    private String description;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String pincode;

    private Double latitude;
    private Double longitude;
}

