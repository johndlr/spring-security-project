package com.juandlr.springsecurityproject.mapper;

import com.juandlr.springsecurityproject.dto.UserDto;
import com.juandlr.springsecurityproject.entity.ApplicationUser;

public class UserMapper {

    public static ApplicationUser mapToUser(UserDto userDto, ApplicationUser user){
        user.setUserName(userDto.getUserName());
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public static UserDto mapToUserDto(UserDto userDto, ApplicationUser user){
        userDto.setUserName(user.getUserName());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
