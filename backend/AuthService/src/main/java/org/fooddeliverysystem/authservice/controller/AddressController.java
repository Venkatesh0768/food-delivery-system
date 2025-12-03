package org.fooddeliverysystem.authservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.fooddeliverysystem.authservice.dto.addressdtos.AddressRequestDto;
import org.fooddeliverysystem.authservice.dto.addressdtos.AddressResponseDto;
import org.fooddeliverysystem.authservice.model.Address;
import org.fooddeliverysystem.authservice.service.impl.AddressServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
@Tag(name = "Address Controller")
public class AddressController {

    private final AddressServiceImpl addressService;

    @Operation(summary = "Create new address for a user")
    @PostMapping("/user/{userId}")
    public ResponseEntity<AddressResponseDto> createAddress(
            @PathVariable UUID userId,
            @RequestBody AddressRequestDto requestDto
    ) {
        return ResponseEntity.ok(addressService.registerAddress(userId, requestDto));
    }

    @Operation(summary = "Get all addresses of a user")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Address>> getAddressByUser(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.ok(addressService.getAddressesByUser(userId));
    }
}
