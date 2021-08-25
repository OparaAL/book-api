package com.book.bookapi.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReaderDto extends UserDto{

    private String about;
    private LocalDate dateOfBirth;

}
