package com.juandlr.springsecurityproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FilterErrorResponse", description = "Schema to hold filter error response information")
public record FilterErrorResponseDto(
        @Schema(description = "Error code representing the error happened", example = "400")
        int errorCode,

        @Schema(description = "Error message representing the error happened", example = "Invalid filter criteria")
        String message) {
}
