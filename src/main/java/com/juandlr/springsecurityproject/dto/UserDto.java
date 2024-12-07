package com.juandlr.springsecurityproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "User", description = "Schema to hold the user information")
@Data @AllArgsConstructor @NoArgsConstructor
public class UserDto {

    @Schema(description = "Represents the username", example = "brucebanner")
    @NotEmpty(message = "Username can not be a null or empty.")
    @Size(min = 5, max = 30, message = "The length of the name should be between 5 and 30.")
    private String userName;

    @Schema(description = "Represents the name of the user", example = "Bruce")
    @NotEmpty(message = "Name can not be a null or empty.")
    @Size(min = 2, max = 30, message = "The length of the name should be between 2 and 30.")
    private String name;

    @Schema(description = "Represents the last name of the user", example = "Banner")
    @NotEmpty(message = "Last name can not be a null or empty.")
    @Size(min = 2, max = 30, message = "The length of the name should be between 2 and 30.")
    private String lastName;

    @Schema(description = "Represents the email of the user", example = "hulkster@example.com")
    @NotEmpty(message = "Email address can not be a null or empty.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "The email format is not valid.")
    private String email;
}
