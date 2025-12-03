package org.fooddeliverysystem.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.fooddeliverysystem.orderservice.dto.RegisterDeliveryBoyRequest;
import org.fooddeliverysystem.orderservice.dto.UpdateLocationRequest;
import org.fooddeliverysystem.orderservice.model.DeliveryAssignment;
import org.fooddeliverysystem.orderservice.model.DeliveryPartner;
import org.fooddeliverysystem.orderservice.repository.DeliveryAssignmentRepository;
import org.fooddeliverysystem.orderservice.repository.DeliveryPartnerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryAssignmentRepository assignmentRepository;
    private final DeliveryPartnerRepository partnerRepository;

    public DeliveryPartner registerPartner(RegisterDeliveryBoyRequest request){
        DeliveryPartner deliveryPartner = DeliveryPartner.builder()
                .name(request.getName())
                .phoneNumber(request.getPhone())
                .vehicleType(request.getVehicleType())
                .status("OFFLINE")
                .build();
        return partnerRepository.save(deliveryPartner);
    }

    public DeliveryPartner setOnline(UUID partnerId) {
        DeliveryPartner dp = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new RuntimeException("Partner not found"));
        dp.setStatus("ONLINE");
        return partnerRepository.save(dp);
    }



}
