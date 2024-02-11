package com.devskiller.library.model;

import java.util.Objects;
import java.util.UUID;

public class BookBorrowing {

    private UUID id;
    private Book book;

    public BookBorrowing(Book book) {
        this.id = UUID.randomUUID();
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookBorrowing that = (BookBorrowing) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
