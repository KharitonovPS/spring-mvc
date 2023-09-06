package com.example.books.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
public class AuthorDTO {
    private Long id;
    private String authorName;
    private String biography;
    private LocalDate authorDateOfBirth;

    public AuthorDTO(String authorName, String biography, LocalDate localDate) {
    }
}
