package org.fooddeliverysystem.authservice.controller;

import jakarta.validation.Valid;
import org.fooddeliverysystem.authservice.dtos.RegisterRequest;
import org.fooddeliverysystem.authservice.dtos.RegisterResponse;
import org.fooddeliverysystem.authservice.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserServiceImpl userService;

    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> signUp(@Valid @RequestBody RegisterRequest requestDto) {
        RegisterResponse response = userService.registerUser(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return new ResponseEntity<>("Login successful", HttpStatus.OK);
    }

}
