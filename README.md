# Library application

## Introduction
You are creating an application allowing a library to manage books count. Your current task is to finish implementation of
missing service methods. Data store is accessed by repository layer to which interfaces are provided in `repository` package.

## Problem statement
Implement `borrowBook` method in the `BookService` class which should:
- Save information that user borrowed particular book from library
- Decrement available book copies count at library by 1
- Throw `BookNotFoundException` if the book is not available in data store
- Throw `BookNotAvailableException` if the book count is already equal to 0
- Throw `BookAlreadyBorrowedException` if the book is already borrowed by the user

---
Implement `returnBook` method in the `BookService` class which should:
- Save information that user returned particular book to library
- Increment available book copies count at library by 1
- Throw `BookBorrowingNotFoundException` if the user does not have such book borrowed in data store

---
Implement `addBookCopy` method in the `BookService` class which should:
- Save a book copies count of 1 if the book is not present in data store
- Increment copies count by 1 if the book is already present in data store

---
Implement `removeBookCopy` method in the `BookService` class which should:
- Decrement copies count by 1 if the book is present in data store
- Throw `BookNotFoundException` if the book is not present in data store
- Throw `BookNotAvailableException` if the book count is already equal to 0

---
Implement `getAvailableBookCopies` method in the `BookService` class which should:
- Return book count if the book is present in data store
- Throw `BookNotFoundException` if the book is not present in data store

## Hints
- Repository interfaces contain Javadocs describing their methods behaviour
- You are not allowed to add/delete/modify methods available in repository interface
- You are not allowed to modify existing tests, but you are free to add new ones
- Remember to keep high code quality
