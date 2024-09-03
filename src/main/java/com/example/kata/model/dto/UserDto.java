package com.example.kata.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String id;
    private String name;
    private List<String> borrowedBookIds;
}