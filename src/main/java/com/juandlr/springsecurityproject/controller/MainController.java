package com.juandlr.springsecurityproject.controller;
import com.juandlr.springsecurityproject.constants.HttpConstants;
import com.juandlr.springsecurityproject.dto.ErrorResponseDto;
import com.juandlr.springsecurityproject.dto.LoginResponseDto;
import com.juandlr.springsecurityproject.dto.ResponseDto;
import com.juandlr.springsecurityproject.dto.UpdatePasswordRequestDto;
import com.juandlr.springsecurityproject.dto.UserDto;
import com.juandlr.springsecurityproject.service.ApplicationUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "CRUD REST API for testing authentication and authorization",
        description = "Testing JWT-based authentication and Role-based authorization"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "bearer-key")
public class MainController {

    private final ApplicationUserService userService;

    @Operation(summary = "Fetch user information", description = "Fetches information of a user by username")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "HTTP Status OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "400", description = "HTTP Status BAD REQUEST", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "HTTP Status NOT FOUND", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/user")
    public ResponseEntity<UserDto> fetchUser(@NotEmpty(message = "Username can not be a null or empty.")
                                             @Size(min = 5, max = 30, message = "The length of the name should be between 5 and 30.")
                                             @RequestParam String userName){
        UserDto userDto = userService.fetchUserInformation(userName);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @Operation(summary = "Delete a user", description = "Deletes a user information")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "HTTP Status OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "HTTP Status BAD REQUEST", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "HTTP Status NOT FOUND", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/user")
    public ResponseEntity<ResponseDto> deleteUser(
            @NotEmpty(message = "Username can not be a null or empty.")
            @Size(min = 5, max = 30, message = "The length of the name should be between 5 and 30.") String userName){
        userService.deleteUser(userName);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(new ResponseDto(HttpConstants.STATUS_200, HttpConstants.MESSAGE_200_DELETE));
    }

    @Operation(summary = "Update user information", description = "Updates information of a user")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "HTTP Status OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "HTTP Status BAD REQUEST", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "HTTP Status CONFLICT", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/user")
    public ResponseEntity<ResponseDto> updateUser(@RequestBody @Valid UserDto userDto){
        userService.updateUser(userDto);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(new ResponseDto(HttpConstants.STATUS_200, HttpConstants.MESSAGE_200_UPDATE));
    }

    @Operation(summary = "Update user password information", description = "Updates information of a user")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "HTTP Status OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "HTTP Status BAD REQUEST", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "HTTP Status CONFLICT", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/user/password")
    public ResponseEntity<ResponseDto> updatePassword(@Valid @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto) {
        userService.updatePassword(updatePasswordRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(new ResponseDto(HttpConstants.STATUS_200, HttpConstants.MESSAGE_200_UPDATE));
    }

}
