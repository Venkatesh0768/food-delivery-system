package org.fooddeliverysystem.authservice.dto.addressdtos;

import lombok.*;
import org.fooddeliverysystem.authservice.model.User;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddressResponseDto {
    private UUID id;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private Double latitude;
    private Double longitude;
    private Instant createdAt;
    private Instant updatedAt;
}

