package com.book.bookapi.mapper.book;

import com.book.bookapi.dto.book.BookDto;
import com.book.bookapi.model.book.BookEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto bookToBookDto(BookEntity bookEntity);

    BookEntity bookDtoToBook(BookDto bookDto);

    List<BookDto> booksToBooksDto(List<BookEntity> bookEntities);
}
