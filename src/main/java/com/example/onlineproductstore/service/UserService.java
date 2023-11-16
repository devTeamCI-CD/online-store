package com.example.onlineproductstore.service;

import com.example.onlineproductstore.dto.user.UserRegistrationRequestDto;
import com.example.onlineproductstore.dto.user.UserResponseDto;
import com.example.onlineproductstore.exception.RegistrationException;
import com.example.onlineproductstore.model.User;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;

    User getAuthenticatedUser();
}
