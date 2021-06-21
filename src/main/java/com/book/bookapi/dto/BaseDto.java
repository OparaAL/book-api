package com.book.bookapi.dto;

import java.io.Serializable;

public class BaseDto implements Serializable {

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
