package com.example.books.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Author implements Comparable<Author> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String authorName;

    private String biography;

    @Past
    private LocalDate authorDateOfBirth;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Book> books;

    public Author(String authorName, String biography, LocalDate authorDateOfBirth) {
        this.authorName = authorName;
        this.biography = biography;
        this.authorDateOfBirth = authorDateOfBirth;
    }
    public Author(Long id, String authorName, String biography, LocalDate authorDateOfBirth) {
        this.id = id;
        this.authorName = authorName;
        this.biography = biography;
        this.authorDateOfBirth = authorDateOfBirth;
    }

    @Override
    public int compareTo(Author author) {
        return this.authorName.compareTo(author.authorName);
    }
}
