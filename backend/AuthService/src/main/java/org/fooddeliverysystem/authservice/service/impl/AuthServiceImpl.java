package org.fooddeliverysystem.authservice.service.impl;

import lombok.AllArgsConstructor;
import org.fooddeliverysystem.authservice.dto.authdtos.RegisterRequest;
import org.fooddeliverysystem.authservice.dto.authdtos.RegisterResponse;
import org.fooddeliverysystem.authservice.model.Provider;
import org.fooddeliverysystem.authservice.model.User;
import org.fooddeliverysystem.authservice.repositories.UserRepository;
import org.fooddeliverysystem.authservice.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }
        if (userRepository.existsByEmail(request.getEmail().trim().toLowerCase())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        String encoded = null;
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            encoded = passwordEncoder.encode(request.getPassword());
        }

        User user = User.builder()
                .email(request.getEmail().trim().toLowerCase())
                .name(request.getName())
                .provider(Provider.LOCAL)
                .password(encoded) // null allowed for OAuth-only users
                .image(request.getImage())
                .enabled(true)
                .build();

        User saved = userRepository.save(user);

        return RegisterResponse.builder()
                .id(saved.getId())
                .email(saved.getEmail())
                .name(saved.getName())
                .image(saved.getImage())
                .enabled(saved.isEnabled())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();
    }
}
