package com.example.kata.service;

// LibraryService.java

import com.example.kata.exceptions.BookAlreadyBorrowedException;
import com.example.kata.exceptions.BookNotFoundException;
import com.example.kata.exceptions.UserNotFoundException;
import com.example.kata.model.dto.BookDto;
import com.example.kata.model.entity.BookEntity;
import com.example.kata.model.entity.UserEntity;
import com.example.kata.repository.BookRepository;
import com.example.kata.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    public LibraryService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public BookEntity addBook(BookDto bookDto) {
        BookEntity bookEntity = BookEntity.builder()
                .title(bookDto.getTitle())
                .build();
        return bookRepository.save(bookEntity);
    }

    @Transactional
    public void borrowBook(Long userId, Long bookId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        if (bookEntity.isBorrowed()) {
            throw new BookAlreadyBorrowedException(bookId);
        }
        bookEntity.setBorrowed(true);
        bookRepository.save(bookEntity);
        userEntity.getBorrowedBookIds().add(bookEntity.getId());
        userRepository.save(userEntity);
    }

    @Transactional
    public void returnBook(Long userId, Long bookId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        if (bookEntity.isBorrowed() && userEntity.getBorrowedBookIds().contains(bookEntity.getId())) {
            bookEntity.setBorrowed(false);
            bookRepository.save(bookEntity);

            userEntity.getBorrowedBookIds().remove(bookEntity.getId());
            userRepository.save(userEntity);
        }
    }

    public List<BookDto> listBorrowedBooks(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return userEntity.getBorrowedBookIds().stream()
                .map(bookRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(BookDto::fromEntity)
                .toList();
    }
}
