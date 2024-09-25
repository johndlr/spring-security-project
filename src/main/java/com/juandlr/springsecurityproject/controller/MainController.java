package com.juandlr.springsecurityproject.controller;
import com.juandlr.springsecurityproject.dto.UserDto;
import com.juandlr.springsecurityproject.service.ApplicationUserService;
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

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class MainController {

    private final ApplicationUserService userService;

    @GetMapping("/user")
    public ResponseEntity<UserDto> fetchUser(@NotEmpty(message = "Username can not be a null or empty.")
                                             @Size(min = 5, max = 30, message = "The length of the name should be between 5 and 30.")
                                             @RequestParam String userName){
        UserDto userDto = userService.fetchUserInformation(userName);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }



}
