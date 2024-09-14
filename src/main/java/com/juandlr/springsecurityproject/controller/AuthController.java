package com.juandlr.springsecurityproject.controller;

import com.juandlr.springsecurityproject.constants.HttpConstants;
import com.juandlr.springsecurityproject.dto.LoginRequestDto;
import com.juandlr.springsecurityproject.dto.ResponseDto;
import com.juandlr.springsecurityproject.dto.SignUpRequestDto;
import com.juandlr.springsecurityproject.service.ApplicationUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@AllArgsConstructor
public class AuthController {

    private final ApplicationUserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto){
        userService.singUpUser(signUpRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpConstants.STATUS_201, HttpConstants.MESSAGE_201));
    }

    public ResponseEntity<ResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        userService.loginUser(loginRequestDto);

    }

}
