package com.juandlr.springsecurityproject.controller;
import com.juandlr.springsecurityproject.dto.ErrorResponseDto;
import com.juandlr.springsecurityproject.dto.UserDto;
import com.juandlr.springsecurityproject.service.ApplicationUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Tag(
        name = "CRUD REST API for testing authentication and authorization",
        description = "Testing JWT-based authentication and Role-based authorization"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class MainController {

    private final ApplicationUserService userService;

    @Operation(
            summary = "Fetch User REST API",
            description = "REST API to fetch user information"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/user")
    public ResponseEntity<UserDto> fetchUser(@NotEmpty(message = "Username can not be a null or empty.")
                                             @Size(min = 5, max = 30, message = "The length of the name should be between 5 and 30.")
                                             @RequestParam String userName){
        UserDto userDto = userService.fetchUserInformation(userName);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @Operation(
            summary = "Get Organization Codes",
            description = "REST API to get fictitious codes from the organization"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/codes")
    public ResponseEntity<Set<String>> getCodes(){
        Set<String> setOfCodes = userService.generateCodes();
        return ResponseEntity.status(HttpStatus.OK).body(setOfCodes);
    }



}
