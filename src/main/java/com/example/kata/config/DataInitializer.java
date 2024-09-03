package com.example.kata.config;

import com.example.kata.model.entity.BookEntity;
import com.example.kata.model.entity.UserEntity;
import com.example.kata.repository.BookRepository;
import com.example.kata.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    public DataInitializer(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Bean
    public CommandLineRunner initializeDatabase() {
        return args -> {
            bookRepository.saveAll(List.of(
                    new BookEntity("Harry Potter and the Philosopher's Stone"),
                    new BookEntity("Harry Potter and the Chamber of Secrets"),
                    new BookEntity("Harry Potter and the Prisoner of Azkaban")
            ));

            userRepository.saveAll(List.of(
                    new UserEntity("user1"),
                    new UserEntity("user2")
            ));
        };
    }
}
