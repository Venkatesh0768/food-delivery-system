package org.fooddeliverysystem.authservice.dto.authdtos;

import java.time.Instant;

public record UserDto(
        String name,
        String email,
        boolean enable,
        String image,
        Instant createdAt,
        Instant updatedAt
) {

}