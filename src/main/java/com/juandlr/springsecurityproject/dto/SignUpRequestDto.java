package com.juandlr.springsecurityproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(name = "Sign Up Request", description = "Schema to hold the sign up information")
public record SignUpRequestDto(@Schema(description = "Represents the username", example = "brucebanner")
                               @NotEmpty(message = "Username can not be a null or empty.")
                               @Size(min = 5, max = 30, message = "The length of the username should be between 5 and 30.")
                               String userName,
                               
                               @Schema(description = "Represents the name", example = "Bruce")
                               @NotEmpty(message = "Name can not be a null or empty.")
                               @Size(min = 2, max = 30, message = "The length of the name should be between 2 and 30.")
                               String name,

                               @NotEmpty(message = "Last Name can not be a null or empty.")
                               @Size(min = 2, max = 30, message = "The length of the name should be between 2 and 30.")
                               @Schema(description = "Represents the last name", example = "Banner")
                               String lastName,

                               @Schema(description = "Represents the email", example = "hulkster@example.com")
                               @NotEmpty(message = "Email address can not be a null or empty.")
                               @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                       message = "The email format is not valid.")
                               String email,

                               @Schema(description = "Represents the password", example = "green@12345")
                               @NotEmpty(message = "Password can not be a null or empty.")
                               @Size(min = 8, max = 30, message = "The length of the password should be between 8 and 30.")
                               String password) {



}
