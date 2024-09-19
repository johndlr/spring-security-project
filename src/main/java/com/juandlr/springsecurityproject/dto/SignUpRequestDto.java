package com.juandlr.springsecurityproject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequestDto(@NotEmpty(message = "Username can not be a null or empty.")
                               @Size(min = 5, max = 30, message = "The length of the username should be between 5 and 30.")
                               String userName,

                               @NotEmpty(message = "Name can not be a null or empty.")
                               @Size(min = 2, max = 30, message = "The length of the name should be between 2 and 30.")
                               String name,

                               @NotEmpty(message = "Last Name can not be a null or empty.")
                               @Size(min = 2, max = 30, message = "The length of the name should be between 2 and 30.")
                               String lastName,

                               @NotEmpty(message = "Email address can not be a null or empty.")
                               @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                       message = "The email format is not valid.")
                               String email,

                               @NotEmpty(message = "Password can not be a null or empty.")
                               @Size(min = 8, max = 30, message = "The length of the password should be between 8 and 30.")
                               String password) {
}
