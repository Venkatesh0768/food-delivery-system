package org.fooddeliverysystem.authservice.dto.userdtos;

import lombok.*;
import org.fooddeliverysystem.authservice.model.Provider;


import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private UUID id;
    private String email;
    private String password;
    private String name;
    private String image;
    private boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
    private Provider provider;
    private Set<RoleDto> roles;
}
