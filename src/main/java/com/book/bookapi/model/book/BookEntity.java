package com.book.bookapi.model.book;

import com.book.bookapi.model.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "book")
@Data
public class BookEntity extends BaseEntity {

    private String name;

    private String author;

    private String publisher;

    private String description;

    private String language;

    private long pageCount;

    private long yearOfPublish;

}
