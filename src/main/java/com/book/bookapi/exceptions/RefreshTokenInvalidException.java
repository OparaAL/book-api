package com.book.bookapi.exceptions;

public class RefreshTokenInvalidException extends RuntimeException{
    public RefreshTokenInvalidException(String message){
        super(message);
    }
}
