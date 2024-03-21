package com.example.onlineproductstore.controller;

import com.example.onlineproductstore.dto.user.UserLoginRequestDto;
import com.example.onlineproductstore.dto.user.UserLoginResponseDto;
import com.example.onlineproductstore.dto.user.UserRegistrationRequestDto;
import com.example.onlineproductstore.dto.user.UserResponseDto;
import com.example.onlineproductstore.exception.RegistrationException;
import com.example.onlineproductstore.model.User;
import com.example.onlineproductstore.service.AuthenticationService;
import com.example.onlineproductstore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication API",
        description = "Controller for registering and authenticating users")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Login user",
            description = "Check if we have such user in DB")
    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }

    @Operation(summary = "Register user",
            description = "Register new user and giving him USER role")
    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }

    @Operation(summary = "Check if user is admin",
            description = "Check if user is admin")
    @GetMapping("/isAdmin")
    public boolean isAdmin() {
        User authenticatedUser = userService.getAuthenticatedUser();
        return authenticatedUser.getRoles()
                .stream()
                .anyMatch(role -> role.getName().name().equals("ROLE_ADMIN"));
    }

    @GetMapping("/user")
    public User getUser() {
        return userService.getAuthenticatedUser();
    }
}
