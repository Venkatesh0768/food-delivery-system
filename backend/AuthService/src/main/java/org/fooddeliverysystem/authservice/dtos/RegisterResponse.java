package org.fooddeliverysystem.authservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String image;
    private boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
}