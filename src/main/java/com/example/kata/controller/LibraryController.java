package com.example.kata.controller;

import com.example.kata.model.dto.BookDto;
import com.example.kata.model.entity.BookEntity;
import com.example.kata.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @PostMapping("/addBook")
    public BookEntity addBook(@RequestBody BookDto bookDto) {
        return libraryService.addBook(bookDto);
    }

    @PostMapping("/borrowBook")
    public void borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        libraryService.borrowBook(userId, bookId);
    }

    @PostMapping("/returnBook")
    public void returnBook(@RequestParam Long userId, @RequestParam Long bookId) {
        libraryService.returnBook(userId, bookId);
    }

    @GetMapping("/borrowedBooks")
    public List<BookDto> listBorrowedBooks(@RequestParam Long userId) {
        return libraryService.listBorrowedBooks(userId);
    }
}