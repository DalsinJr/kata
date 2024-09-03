package com.example.kata.model.dto;

import com.example.kata.model.entity.BookEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {

    private String title;
    private boolean isBorrowed;
    public static BookDto fromEntity(BookEntity book) {
        return BookDto.builder()
                .title(book.getTitle())
                .build();
    }
}