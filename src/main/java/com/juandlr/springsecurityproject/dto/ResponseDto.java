package com.juandlr.springsecurityproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Response",description = "Schema to hold successful response information")
public record ResponseDto(
        @Schema(description = "Status code in the response", example = "201")String statusCode,
        @Schema(description = "Status message in the response", example = "CREATED")String statusMsg) {
}
