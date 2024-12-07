package com.juandlr.springsecurityproject.exception;

public class UserNameIsUniqueException extends RuntimeException{
    public UserNameIsUniqueException(String message) {
        super(message);
    }
}
