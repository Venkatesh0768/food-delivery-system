package org.fooddeliverysystem.authservice.service;


import org.fooddeliverysystem.authservice.dto.authdtos.RegisterRequest;
import org.fooddeliverysystem.authservice.dto.authdtos.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
}
