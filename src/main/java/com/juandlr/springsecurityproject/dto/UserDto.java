package com.juandlr.springsecurityproject.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserDto {

    @NotEmpty(message = "Username can not be a null or empty.")
    @Size(min = 5, max = 30, message = "The length of the name should be between 5 and 30.")
    private String userName;

    @NotEmpty(message = "Name can not be a null or empty.")
    @Size(min = 2, max = 30, message = "The length of the name should be between 2 and 30.")
    private String name;

    @NotEmpty(message = "Last name can not be a null or empty.")
    @Size(min = 2, max = 30, message = "The length of the name should be between 2 and 30.")
    private String lastName;

    @NotEmpty(message = "Email address can not be a null or empty.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "The email format is not valid.")
    private String email;
}
