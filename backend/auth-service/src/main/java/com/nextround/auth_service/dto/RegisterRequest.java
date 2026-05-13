package com.nextround.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Invalid email")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
    private String phoneNumber;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}