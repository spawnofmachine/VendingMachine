package com.example.Vending.Machine.Models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotBlank(message = "Username is required")
    @Min(value = 5, message = "Username must be at least 5 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Min(value = 5, message = "Password must be at least 5 characters")
    private String password;

    @NotBlank(message = "First name is required")
    @Min(value = 5, message = "First name must be at least 5 characters")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Min(value = 5, message = "Last namemust be at least 5 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Min(value = 5, message = "Email must be at least 5 characters")
    private String email;
}
