package com.example.books.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;

    private String book;
    private String authorName;
    private String biography;
    private LocalDate authorDateOfBirth;

    public Author( String book, String authorName, String biography, LocalDate authorDateOfBirth) {
        this.book = book;
        this.authorName = authorName;
        this.biography = biography;
        this.authorDateOfBirth = authorDateOfBirth;
    }
}
