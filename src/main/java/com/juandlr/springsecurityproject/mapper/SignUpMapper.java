package com.juandlr.springsecurityproject.mapper;

import com.juandlr.springsecurityproject.dto.SignUpRequestDto;
import com.juandlr.springsecurityproject.entity.ApplicationUser;

public class SignUpMapper {

    public static ApplicationUser mapToApplicationUser(ApplicationUser applicationUser, SignUpRequestDto signUpRequestDto, String hashedPassword){
        applicationUser.setUserName(signUpRequestDto.userName());
        applicationUser.setName(signUpRequestDto.name());
        applicationUser.setLastName(signUpRequestDto.lastName());
        applicationUser.setEmail(signUpRequestDto.email());
        applicationUser.setPassword(hashedPassword);
        return applicationUser;
    }
}
