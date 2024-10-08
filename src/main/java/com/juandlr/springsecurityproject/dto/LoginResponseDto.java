package com.juandlr.springsecurityproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Login Request", description = "Schema to hold the jwt information")
public record LoginResponseDto(@Schema( description = "Represent the JWT generated by the server")String jwt) {
}
