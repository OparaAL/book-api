package com.book.bookapi.exceptions;

public class ItemAlreadyExistsException extends RuntimeException{
    public ItemAlreadyExistsException(String message){
        super(message);
    };
}
