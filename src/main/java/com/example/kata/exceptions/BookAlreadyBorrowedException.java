package com.example.kata.exceptions;

public class BookAlreadyBorrowedException extends RuntimeException {
    public BookAlreadyBorrowedException(Long bookId) {
        super("Book with ID " + bookId + " is already borrowed.");
    }
}