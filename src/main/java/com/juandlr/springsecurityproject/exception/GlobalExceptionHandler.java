package com.juandlr.springsecurityproject.exception;

import com.juandlr.springsecurityproject.dto.ErrorResponseDto;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> validationErrors = new ArrayList<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();
        validationErrorList.forEach((error) -> {
            String validationMsg = error.getDefaultMessage();
            validationErrors.add(validationMsg);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception,WebRequest webRequest) {
       return new ResponseEntity<>(errorResponseDtoGenerator(exception,webRequest), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExistsException(UserAlreadyExistsException exception,WebRequest webRequest){
        return new ResponseEntity<>(errorResponseDtoGenerator(exception,webRequest), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleNameNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleRoleNameNotFoundException(RoleNameNotFoundException exception, WebRequest webRequest){
        return new ResponseEntity<>(errorResponseDtoGenerator(exception,webRequest), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException exception,WebRequest webRequest){
        return new ResponseEntity<>(errorResponseDtoGenerator(exception,webRequest), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNameIsUniqueException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNameIsUniqueException(UserNameIsUniqueException exception,WebRequest webRequest){
        return new ResponseEntity<>(errorResponseDtoGenerator(exception,webRequest), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponseDto errorResponseDtoGenerator(Exception exception, WebRequest webRequest){
        return new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
    }


}
