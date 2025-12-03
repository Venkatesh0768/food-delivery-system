package org.fooddeliverysystem.restaurantservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Service
public class MenuRequest {
    private UUID restaurantId;
    private String name;
    private String description;
}
