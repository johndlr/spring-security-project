package com.juandlr.springsecurityproject.exception;

public class RoleNameNotFoundException extends RuntimeException{

    public RoleNameNotFoundException(String message) {
        super(message);
    }
}
