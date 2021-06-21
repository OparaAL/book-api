package com.book.bookapi.repository.book;

import com.book.bookapi.model.book.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
