package com.book.bookapi.service.interfaces.book;

import com.book.bookapi.dto.book.BookDto;
import com.book.bookapi.dto.book.SearchBookDto;

import java.util.List;


public interface BookService {

    BookDto get(Long bookId);

    List<BookDto> getAll();

    BookDto create(BookDto bookDto);

    BookDto update(Long bookId, BookDto bookDto);

    List<BookDto> searchBooks(SearchBookDto searchBookDto);
}
