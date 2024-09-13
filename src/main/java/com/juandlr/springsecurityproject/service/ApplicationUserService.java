package com.juandlr.springsecurityproject.service;

import com.juandlr.springsecurityproject.dto.SignUpRequestDto;


public interface ApplicationUserService {
    void singUpUser(SignUpRequestDto signUpRequestDto);
}
