package org.fooddeliverysystem.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.fooddeliverysystem.authservice.dto.addressdtos.AddressRequestDto;
import org.fooddeliverysystem.authservice.dto.addressdtos.AddressResponseDto;
import org.fooddeliverysystem.authservice.model.Address;
import org.fooddeliverysystem.authservice.model.User;
import org.fooddeliverysystem.authservice.repositories.AddressRepository;
import org.fooddeliverysystem.authservice.repositories.UserRepository;
import org.fooddeliverysystem.authservice.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Override
    public AddressResponseDto registerAddress(UUID userId, AddressRequestDto dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Address address = new Address();
        address.setUser(user);
        address.setAddressLine1(dto.getAddressLine1());
        address.setAddressLine2(dto.getAddressLine2());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        address.setLatitude(dto.getLatitude());
        address.setLongitude(dto.getLongitude());

        Address saved = addressRepository.save(address);

        return toDto(saved);
    }


    public List<Address> getAddressesByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return addressRepository.findByUserId(userId);
    }


    private AddressResponseDto toDto(Address address) {
        AddressResponseDto dto = new AddressResponseDto();
        dto.setId(address.getId());
        dto.setAddressLine1(address.getAddressLine1());
        dto.setAddressLine2(address.getAddressLine2());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setPostalCode(address.getPostalCode());
        dto.setCountry(address.getCountry());
        dto.setLatitude(address.getLatitude());
        dto.setLongitude(address.getLongitude());
        dto.setCreatedAt(address.getCreatedAt());
        dto.setUpdatedAt(address.getUpdatedAt());
        return dto;
    }


}
