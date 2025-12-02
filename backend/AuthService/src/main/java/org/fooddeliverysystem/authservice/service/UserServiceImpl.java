package org.fooddeliverysystem.authservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fooddeliverysystem.authservice.dtos.RegisterRequest;
import org.fooddeliverysystem.authservice.dtos.RegisterResponse;
import org.fooddeliverysystem.authservice.model.Provider;
import org.fooddeliverysystem.authservice.model.User;
import org.fooddeliverysystem.authservice.repository.RoleRepository;
import org.fooddeliverysystem.authservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;  // New: For default roles
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse registerUser(RegisterRequest request) {
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
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .provider(Provider.LOCAL)
                .password(encoded) // null allowed for OAuth-only users
                .image(request.getImage())
                .enabled(true)
                .build();

        User saved = userRepository.save(user);

        return RegisterResponse.builder()
                .id(saved.getId())
                .email(saved.getEmail())
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .image(saved.getImage())
                .enabled(saved.isEnabled())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();
    }
}