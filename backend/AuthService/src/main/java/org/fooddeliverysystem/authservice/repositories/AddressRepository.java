package org.fooddeliverysystem.authservice.repositories;

import org.fooddeliverysystem.authservice.dto.addressdtos.AddressResponseDto;
import org.fooddeliverysystem.authservice.model.Address;
import org.fooddeliverysystem.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address , UUID> {
    List<Address> findByUserId(UUID userId);
}
