package com.book.bookapi.mapper.user;

import com.book.bookapi.dto.request.SignUpDto;
import com.book.bookapi.model.user.credentials.ApplicationCredentialsEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationCredentialsMapper {
    ApplicationCredentialsEntity signUpDtoToCredentials(SignUpDto signUpDto);
}
