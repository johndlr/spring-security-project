package com.juandlr.springsecurityproject.service;

import com.juandlr.springsecurityproject.dto.LoginRequestDto;
import com.juandlr.springsecurityproject.dto.SignUpRequestDto;
import com.juandlr.springsecurityproject.dto.UserDto;


public interface ApplicationUserService {
    void singUpUser(SignUpRequestDto signUpRequestDto);

    String loginUser(LoginRequestDto loginRequestDto);

    UserDto fetchUserInformation(String userName);
}
