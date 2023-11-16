package com.example.onlineproductstore.service;

import com.example.onlineproductstore.dto.user.UserLoginRequestDto;
import com.example.onlineproductstore.dto.user.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto authenticate(UserLoginRequestDto request);

}
