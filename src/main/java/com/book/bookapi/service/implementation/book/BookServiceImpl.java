package com.book.bookapi.service.implementation.book;

import com.book.bookapi.dto.book.BookDto;
import com.book.bookapi.dto.book.SearchBookDto;
import com.book.bookapi.exceptions.ItemNotFoundException;
import com.book.bookapi.mapper.book.BookMapper;
import com.book.bookapi.model.book.BookEntity;
import com.book.bookapi.repository.book.BookRepository;
import com.book.bookapi.service.interfaces.book.BookService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    public BookServiceImpl(BookMapper bookMapper, BookRepository bookRepository) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
    }

    public BookDto get(Long bookId){

        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ItemNotFoundException("Book not found"));

        return bookMapper.bookToBookDto(book);
    }

    public List<BookDto> getAll(){
        return bookMapper.booksToBooksDto(bookRepository.findAll());
    }

    public BookDto create(BookDto bookDto) {

        BookEntity book = bookMapper.bookDtoToBook(bookDto);
        bookRepository.save(book);

        return bookMapper.bookToBookDto(book);
    }

    public BookDto update(Long bookId, BookDto bookDto) {

        BookEntity findBook = bookRepository
                .findById(bookId).orElseThrow(() -> new ItemNotFoundException("Book not found"));

        BookEntity book = bookMapper.bookDtoToBook(bookDto);
        book.setId(findBook.getId());

        bookRepository.save(book);

        return bookMapper.bookToBookDto(book);
    }

    public List<BookDto> searchBooks(SearchBookDto searchBookDto) {
        List<BookEntity> books = bookRepository.findAll(searchBookSpecification(searchBookDto));
        return bookMapper.booksToBooksDto(books);
    }


    private Specification<BookEntity> searchBookSpecification(SearchBookDto searchBookDto) {
        return (root, cq, cb) -> {
            List<Predicate> resultPredicates = new ArrayList<>();

            String name = searchBookDto.getName();
            Optional<Predicate> searchPredicateOptional = bookNamePredicate(name, root, cb);
            if (searchPredicateOptional.isPresent()) {
                resultPredicates.add(searchPredicateOptional.get());
            }

            return cb.and(resultPredicates.toArray(new Predicate[resultPredicates.size()]));
        };
    }

    private Optional<Predicate> bookNamePredicate(String name, Root<BookEntity> root, CriteriaBuilder cb) {

        final Collection<Predicate> predicates = new ArrayList<>();

        if (!name.isEmpty()) {
            name = "%" + name.toLowerCase() + "%";
            final Predicate companyNamePredicate = cb.like(cb.lower(root.get("name")), name);
            predicates.add(companyNamePredicate);
        }
        if (!predicates.isEmpty()) return Optional.of(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        return Optional.empty();
    }
}
