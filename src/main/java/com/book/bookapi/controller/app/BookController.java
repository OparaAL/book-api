package com.book.bookapi.controller.app;

import com.book.bookapi.dto.book.BookDto;
import com.book.bookapi.dto.book.SearchBookDto;
import com.book.bookapi.service.interfaces.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> get(@PathVariable Long bookId){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.get(bookId));
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.status(HttpStatus.OK).body("Hello");
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDto>> searchBooks(SearchBookDto searchBookDto){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.searchBooks(searchBookDto));
    }
}
