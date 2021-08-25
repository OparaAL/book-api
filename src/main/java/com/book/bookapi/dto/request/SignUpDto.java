package com.book.bookapi.dto.request;

import com.book.bookapi.model.user.credentials.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignUpDto {

    @Email
    private String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Za-z]).{8,}$")
    private String password;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotNull
    private UserRole role;


}
