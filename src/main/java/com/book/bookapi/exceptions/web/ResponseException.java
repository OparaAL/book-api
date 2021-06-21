package com.book.bookapi.exceptions.web;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ResponseException  {

    private String message;
    private LocalDateTime timeStamp = LocalDateTime.now(ZoneId.of("UTC"));

    public ResponseException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
