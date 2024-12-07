package com.juandlr.springsecurityproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
@Schema(name = "Login Request", description = "Schema to hold login information from the user")
public record LoginRequestDto(@Schema( description = "Represent the username from the user", example = "brucebanner")
                              @NotEmpty(message = "Username can not be a null or empty.")
                              @Size(min = 5, max = 30, message = "The length of the name should be between 5 and 30.")
                              String userName,
                              @Schema( description = "Represent the password from the user", example = "green@12345")
                              @NotEmpty(message = "Password can not be a null or empty.")
                              @Size(min = 8, max = 30, message = "The length of the password should be between 8 and 30.")
                              String password) {
}
