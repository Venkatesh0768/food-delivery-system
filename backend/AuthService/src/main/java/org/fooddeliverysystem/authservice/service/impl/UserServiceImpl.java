package org.fooddeliverysystem.authservice.service.impl;

import jakarta.transaction.Transactional;

import org.fooddeliverysystem.authservice.dto.userdtos.UserRequestDto;
import org.fooddeliverysystem.authservice.dto.userdtos.UserResponseDto;
import org.fooddeliverysystem.authservice.exception.EmailAlreadyExistException;
import org.fooddeliverysystem.authservice.exception.UserNotFoundException;
import org.fooddeliverysystem.authservice.model.Provider;
import org.fooddeliverysystem.authservice.model.User;
import org.fooddeliverysystem.authservice.repositories.UserRepository;
import org.fooddeliverysystem.authservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public User saveUserIfNotExit(String providerId, String email, String username, String image, Provider provider) {


        User user = userRepository.findByEmail(email).orElseGet(() -> {
            return User.builder()
                    .providerId(providerId)
                    .email(email)
                    .name(username)
                    .provider(provider)
                    .image(image)
                    .password("")
                    .enabled(true)
                    .build();
        });
        return userRepository.save(user);


    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRequestDto.getEmail() != null && userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new EmailAlreadyExistException("Email already in use");
        }

        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        User user = modelMapper.map(userRequestDto, User.class);
        user.setProvider(Provider.LOCAL);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto updateUser(UUID id, UserRequestDto userRequestDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        if (userRequestDto.getEmail() != null && !userRequestDto.getEmail().equals(existingUser.getEmail())
                && userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new EmailAlreadyExistException("Email already in use");
        }

        modelMapper.map(userRequestDto, existingUser);
        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserResponseDto.class);
    }

    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);

    }

    @Override
    @Transactional
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .toList();
    }

}
