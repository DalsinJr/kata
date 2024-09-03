package com.example.kata.controller;

import com.example.kata.model.dto.BookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test adding a new book to the library")
    public void testAddBook() throws Exception {
        BookDto bookDto = BookDto.builder()
                .title("Harry Potter and the Goblet of Fire")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/library/addBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Harry Potter and the Goblet of Fire"))
                .andExpect(jsonPath("$.borrowed").value(false));
    }

    @Test
    @DisplayName("Test listing all borrowed books for a specific user")
    public void testListBorrowedBooksForUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/borrowedBooks")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Test borrowing a book with a non-existent user ID")
    public void testBorrowBookWithNonExistentUserId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/library/borrowBook")
                        .param("userId", "999")  // Non-existent user ID
                        .param("bookId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with ID 999 not found."));
    }

    @Test
    @DisplayName("Test borrowing a book with a non-existent book ID")
    public void testBorrowBookWithNonExistentBookId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/library/borrowBook")
                        .param("userId", "1")
                        .param("bookId", "999")  // Non-existent book ID
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book with ID 999 not found."));
    }

    @Test
    @DisplayName("Test borrowing a book that is already borrowed")
    public void testBorrowAlreadyBorrowedBook() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/library/borrowBook")
                        .param("userId", "1")
                        .param("bookId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Attempt to borrow the same book again
        mockMvc.perform(MockMvcRequestBuilders.post("/library/borrowBook")
                        .param("userId", "1")
                        .param("bookId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Book with ID 1 is already borrowed."));
    }

    @Test
    @DisplayName("Test returning a book with a non-existent user ID")
    public void testReturnBookWithNonExistentUserId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/library/returnBook")
                        .param("userId", "999")
                        .param("bookId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with ID 999 not found."));
    }

    @Test
    @DisplayName("Test returning a book with a non-existent book ID")
    public void testReturnBookWithNonExistentBookId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/library/returnBook")
                        .param("userId", "1")
                        .param("bookId", "999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book with ID 999 not found."));
    }
}
