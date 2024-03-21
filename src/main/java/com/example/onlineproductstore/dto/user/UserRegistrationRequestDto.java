package com.example.onlineproductstore.dto.user;

import com.example.onlineproductstore.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(field = "password",
        fieldToMatch = "repeatPassword",
        message = "Passwords don't match")
public class UserRegistrationRequestDto {
    @NotNull
    @Email
    private String email;
    @NotNull
    @Length(min = 8, max = 255)
    private String password;
    @NotNull
    @Length(min = 8, max = 255)
    private String repeatPassword;
    @NotNull
    @Pattern(regexp = "^\\+?3?8?(0\\d{9})$")
    private String phoneNumber;
    @NotNull
    @Length(min = 1, max = 255)
    private String firstName;
    @NotNull
    @Length(min = 1, max = 255)
    private String lastName;
    @Length(min = 1, max = 255)
    private String shippingAddress;
}
