package com.book.bookapi.mapper.user;

import com.book.bookapi.dto.request.SignUpDto;
import com.book.bookapi.dto.user.ReaderDto;
import com.book.bookapi.dto.user.UserDto;
import com.book.bookapi.model.user.ReaderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReaderMapper {

    ReaderEntity signUpDtoToReader(SignUpDto signUpDto);

    UserDto readerToUserDto(ReaderEntity readerEntity);

    ReaderDto readerToReaderDto(ReaderEntity readerEntity);

    ReaderEntity readerDtoToReader(ReaderDto readerDto);
}
