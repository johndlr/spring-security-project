package com.juandlr.springsecurityproject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(@NotEmpty(message = "Username can not be a null or empty.")
                              @Size(min = 5, max = 30, message = "The length of the name should be between 5 and 30.")
                              String userName,
                              @NotEmpty(message = "Password can not be a null or empty.")
                              @Size(min = 8, max = 30, message = "The length of the password should be between 8 and 30.")
                              String password) {
}
