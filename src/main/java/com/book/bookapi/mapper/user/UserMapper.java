package com.book.bookapi.mapper.user;

import com.book.bookapi.dto.request.SignUpDto;
import com.book.bookapi.dto.user.UserDto;
import com.book.bookapi.model.user.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity signUpToUser(SignUpDto signUpDto);

    UserDto userToDto(UserEntity userEntity);
}
