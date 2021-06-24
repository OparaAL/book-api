package com.book.bookapi.controller.secured;

import com.book.bookapi.dto.book.BookDto;
import com.book.bookapi.service.interfaces.book.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secured/")
@PreAuthorize("hasAuthority(T(com.book.bookapi.model.user.Permission).ADMIN.getPermission())")
public class AdminController {

    private final BookService bookService;

    public AdminController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("book")
    public ResponseEntity<BookDto> create(@RequestBody BookDto bookDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(bookDto));
    }

    @PutMapping("book/{bookId}")
    public ResponseEntity<BookDto> update(@PathVariable Long bookId, @RequestBody BookDto bookDto){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.update(bookId, bookDto));
    }
}
