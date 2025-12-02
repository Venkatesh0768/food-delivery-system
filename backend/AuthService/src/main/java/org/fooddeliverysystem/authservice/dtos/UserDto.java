package org.fooddeliverysystem.authservice.dtos;

import lombok.*;
import org.fooddeliverysystem.authservice.model.Address;
import org.fooddeliverysystem.authservice.model.Provider;
import org.fooddeliverysystem.authservice.model.Role;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class UserDto {
    private UUID id;
    private String providerId;

    private String firstName;
    private String lastName;
    private String image;
    private boolean enabled;
    private String phoneNumber;
    private String email;
    private String password;

    private List<Address> addresses;
    private Provider provider;
    private Set<Role> roles;

    private Instant createdAt;
    private Instant updatedAt;
}
