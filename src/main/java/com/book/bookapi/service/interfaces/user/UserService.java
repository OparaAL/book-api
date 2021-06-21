package com.book.bookapi.service.interfaces.user;

import com.book.bookapi.dto.request.SignUpDto;
import com.book.bookapi.dto.user.UserDto;

public interface UserService {

    UserDto createUser(SignUpDto signUpDto);
}
