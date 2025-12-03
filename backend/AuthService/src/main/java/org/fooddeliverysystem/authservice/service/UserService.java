package org.fooddeliverysystem.authservice.service;


import org.fooddeliverysystem.authservice.dto.userdtos.UserRequestDto;
import org.fooddeliverysystem.authservice.dto.userdtos.UserResponseDto;
import org.fooddeliverysystem.authservice.model.Provider;
import org.fooddeliverysystem.authservice.model.User;

import java.util.UUID;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);
    UserResponseDto getUserByEmail(String email);
    UserResponseDto updateUser(UUID id , UserRequestDto userRequestDto);
    void deleteUser(UUID id);
    Iterable<UserResponseDto> getAllUsers();
    User saveUserIfNotExit(String providerId, String email, String username, String image, Provider provider);
}
