package org.fooddeliverysystem.authservice.service;

import org.fooddeliverysystem.authservice.dto.addressdtos.AddressRequestDto;
import org.fooddeliverysystem.authservice.dto.addressdtos.AddressResponseDto;

import java.util.UUID;

public interface AddressService {
    AddressResponseDto registerAddress(UUID userId, AddressRequestDto addressRequestDto);
}
