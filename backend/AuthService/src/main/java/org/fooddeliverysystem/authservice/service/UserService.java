package org.fooddeliverysystem.authservice.service;

import org.fooddeliverysystem.authservice.dtos.RegisterRequest;
import org.fooddeliverysystem.authservice.dtos.RegisterResponse;

public interface UserService {
    RegisterResponse registerUser(RegisterRequest requestDto);
}
