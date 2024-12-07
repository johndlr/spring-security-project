package com.juandlr.springsecurityproject.exception;

import com.juandlr.springsecurityproject.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception, WebRequest webRequest) {
        return new ResponseEntity<>(errorResponseDtoGenerator(exception, webRequest, HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExistsException(UserAlreadyExistsException exception,WebRequest webRequest){
        return new ResponseEntity<>(errorResponseDtoGenerator(exception, webRequest, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RoleNameNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleRoleNameNotFoundException(RoleNameNotFoundException exception, WebRequest webRequest){
        return new ResponseEntity<>(errorResponseDtoGenerator(exception, webRequest, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException exception, WebRequest webRequest) {
        return new ResponseEntity<>(errorResponseDtoGenerator(exception, webRequest, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNameIsUniqueException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNameIsUniqueException(UserNameIsUniqueException exception,WebRequest webRequest){
        return new ResponseEntity<>(errorResponseDtoGenerator(exception, webRequest, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }

    private ErrorResponseDto errorResponseDtoGenerator(Exception exception, WebRequest webRequest, HttpStatus status) {
        return new ErrorResponseDto(
                webRequest.getDescription(false),
                status,
                exception.getMessage(),
                LocalDateTime.now()
        );
    }
}
