package com.example.onlineproductstore.service.impl;

import com.example.onlineproductstore.dto.user.UserRegistrationRequestDto;
import com.example.onlineproductstore.dto.user.UserResponseDto;
import com.example.onlineproductstore.enums.RoleName;
import com.example.onlineproductstore.exception.RegistrationException;
import com.example.onlineproductstore.mapper.UserMapper;
import com.example.onlineproductstore.model.User;
import com.example.onlineproductstore.repository.user.RoleRepository;
import com.example.onlineproductstore.repository.user.UserRepository;
import com.example.onlineproductstore.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("User with email "
                    + request.getEmail() + " already exists");
        }
        User user = userMapper.toModel(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(roleRepository.getRoleByName(RoleName.ROLE_USER)));
        User savedUser = userRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }

    @Override
    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
