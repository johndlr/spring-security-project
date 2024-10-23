package com.juandlr.springsecurityproject.service;

import com.juandlr.springsecurityproject.dto.LoginRequestDto;
import com.juandlr.springsecurityproject.dto.SignUpRequestDto;
import com.juandlr.springsecurityproject.dto.UserDto;

import java.util.Set;


public interface ApplicationUserService {
    void signUpUser(SignUpRequestDto signUpRequestDto);

    String loginUser(LoginRequestDto loginRequestDto);

    UserDto fetchUserInformation(String userName);

    Set<String> generateCodes();

    void deleteUser(String userName);

    void updateUser(UserDto userDto);
}
