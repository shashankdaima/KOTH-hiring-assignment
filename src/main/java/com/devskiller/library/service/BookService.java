package com.devskiller.library.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.devskiller.library.exception.BookAlreadyBorrowedException;
import com.devskiller.library.exception.BookBorrowingNotFoundException;
import com.devskiller.library.exception.BookNotAvailableException;
import com.devskiller.library.exception.BookNotBorrowedByUserException;
import com.devskiller.library.exception.BookNotFoundException;
import com.devskiller.library.model.Book;
import com.devskiller.library.model.BookBorrowing;
import com.devskiller.library.model.User;
import com.devskiller.library.repository.BooksRepository;
import com.devskiller.library.repository.BorrowingsRepository;

public class BookService {
    private final BooksRepository booksRepository;
    private final BorrowingsRepository borrowingsRepository;

    public BookService(BooksRepository booksRepository, BorrowingsRepository borrowingsRepository) {
        this.booksRepository = booksRepository;
        this.borrowingsRepository = borrowingsRepository;
    }

    public void borrowBook(User user, Book book) {
        Optional<Integer> bookAvailablity = booksRepository.getBookCount(book);
        if (!bookAvailablity.isPresent()) {
            throw new BookNotFoundException();
        }
        if (bookAvailablity.get() <= 0) {
            throw new BookNotAvailableException();
        }
        List<BookBorrowing> booksBorrowedByUser = borrowingsRepository.getByUser(user);
        if (booksBorrowedByUser.contains(book)) {
            throw new BookAlreadyBorrowedException();
        }
        booksBorrowedByUser.add(new BookBorrowing(book));
        borrowingsRepository.save(user, booksBorrowedByUser);
        booksRepository.save(book, bookAvailablity.get() - 1);
    }

    public void returnBook(User user, Book book) {
        Optional<Integer> bookAvailability = booksRepository.getBookCount(book);
        List<BookBorrowing> booksBorrowedByUser = borrowingsRepository.getByUser(user);
        
        booksBorrowedByUser.removeIf(new Predicate<BookBorrowing>() {

            @Override
            public boolean test(BookBorrowing bookBorrowing) {
                return bookBorrowing.getBook().equals(book);
            }

        });
        borrowingsRepository.save(user, booksBorrowedByUser);
        booksRepository.save(book, bookAvailability.get() + 1);

    }

    public void addBookCopy(Book book) {
        Optional<Integer> bookAvailability = booksRepository.getBookCount(book);
        if (!bookAvailability.isPresent()) {
            booksRepository.save(book, 1);
        } else {
            booksRepository.save(book, bookAvailability.get() + 1);
        }
    }

    // Decrement copies count by 1 if the book is present in data store
    // Throw if the book is not present in data store
    // Throw if the book count is already equal to 0
    public void removeBookCopy(Book book) {
        Optional<Integer> bookAvailability = booksRepository.getBookCount(book);
        if (!bookAvailability.isPresent()) {
            throw new BookNotFoundException();
        } else if (bookAvailability.get() == 0) {
            throw new BookNotAvailableException();
        } else {
            booksRepository.save(book, bookAvailability.get() - 1);
        }
    }
    // Return book count if the book is present in data store
    // Throw  if the book is not present in data store
    public int getAvailableBookCopies(Book book) {
        Optional<Integer> bookAvailability = booksRepository.getBookCount(book);
        if (!bookAvailability.isPresent()) {
            throw new BookNotFoundException();
        } else{
            return bookAvailability.get();
        }
    }
}
