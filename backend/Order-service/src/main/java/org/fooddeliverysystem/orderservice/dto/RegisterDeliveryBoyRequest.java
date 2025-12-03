package org.fooddeliverysystem.orderservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDeliveryBoyRequest {
    private String name;
    private String phone;
    private String vehicleType;
}
