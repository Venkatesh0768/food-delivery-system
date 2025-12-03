package org.fooddeliverysystem.authservice.dto.userdtos;


import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {
    private UUID id;
    private String name; // ROLE_USER, ROLE_ADMIN
}
