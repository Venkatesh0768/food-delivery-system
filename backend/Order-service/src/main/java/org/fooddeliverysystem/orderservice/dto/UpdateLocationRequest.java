package org.fooddeliverysystem.orderservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateLocationRequest {
    private Double lat;
    private Double lon;
}
