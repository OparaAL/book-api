package com.book.bookapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BaseDto implements Serializable {
    protected Long id;
}
