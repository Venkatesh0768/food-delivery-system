package org.fooddeliverysystem.orderservice.repository;

import org.fooddeliverysystem.orderservice.model.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, UUID> {
    List<DeliveryPartner> findByStatus(String status);
}
