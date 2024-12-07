package com.juandlr.springsecurityproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;



@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
public record ErrorResponseDto(
        @Schema(description = "API path invoked by client", example = "uri=/api/user")
        String apiPath,

        @Schema(description = "Error code representing the error happened", example = "BAD REQUEST")
        HttpStatus errorCode,

        @Schema(description = "Error message representing the error happened", example = "User does not exists")
        String errorMessage,

        @Schema(description = "Time representing when the error happened", example = "2023-10-01T12:34:56")
        LocalDateTime errorTime) {
}
