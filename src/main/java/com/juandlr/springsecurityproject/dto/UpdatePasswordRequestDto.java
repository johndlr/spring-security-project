package com.juandlr.springsecurityproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Schema(name = "UpdatePasswordRequest", description = "Schema to hold user password update information")
@Data @AllArgsConstructor @NoArgsConstructor
public class UpdatePasswordRequestDto {

    @Schema(description = "Username of the user", example = "martinADD")
    @NotEmpty(message = "Username can not be a null or empty.")
    @Size(min = 5, max = 30, message = "The length of the name should be between 5 and 30.")
    private String userName;

    @Schema(description = "New password for the user", example = "newPassword123")
    @NotEmpty(message = "Password can not be a null or empty.")
    @Size(min = 8, message = "The length of the password should be at least 8 characters.")
    private String newPassword;

}
