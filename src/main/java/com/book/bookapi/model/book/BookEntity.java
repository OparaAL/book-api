package com.book.bookapi.model.book;

import com.book.bookapi.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class BookEntity extends BaseEntity {

    private String name;

    private String author;

    private String publisher;

    private String description;

    private String language;

    private long pageCount;

    private long yearOfPublish;

    public BookEntity() {
    }

    public BookEntity(String name, String author, String publisher, String description, String language, long pageCount, long yearOfPublish) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.language = language;
        this.pageCount = pageCount;
        this.yearOfPublish = yearOfPublish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public long getYearOfPublish() {
        return yearOfPublish;
    }

    public void setYearOfPublish(long yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
    }
}
