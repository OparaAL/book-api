package com.book.bookapi.exceptions;

import com.book.bookapi.exceptions.web.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseException itemNotFoundException(ItemNotFoundException ex){
        return new ResponseException(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ItemAlreadyExistsException.class)
    public ResponseException itemAlreadyExistsException(ItemAlreadyExistsException ex){
        return new ResponseException(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseException jwtAuthenticationException(JwtAuthenticationException ex){
        return new ResponseException(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RefreshTokenInvalidException.class)
    public ResponseException refreshTokenInvalidException(RefreshTokenInvalidException ex){
        return new ResponseException(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongUserException.class)
    public ResponseException wrongUserException(WrongUserException ex){
        return new ResponseException(ex.getMessage());
    }
}