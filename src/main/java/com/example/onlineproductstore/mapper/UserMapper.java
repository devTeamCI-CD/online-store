package com.example.onlineproductstore.mapper;

import com.example.onlineproductstore.config.MapperConfig;
import com.example.onlineproductstore.dto.user.UserLoginResponseDto;
import com.example.onlineproductstore.dto.user.UserRegistrationRequestDto;
import com.example.onlineproductstore.dto.user.UserResponseDto;
import com.example.onlineproductstore.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserLoginResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto requestDto);

    UserResponseDto toResponseDto(User user);
}
