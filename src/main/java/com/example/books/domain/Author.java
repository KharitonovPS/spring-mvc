package com.example.books.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;

    /*@NotBlank(message = "Name may not be blank!")
    @Length(max = 255)*/
    private String authorName;

    /*@NotBlank(message = "Biography may not be blank!")
    @Length(max = 2048, message = "Message too long (more than 2kB)")*/
    private String biography;

//    @NotBlank(message = "Date of birth may not be blank!")
    @Past
    private LocalDate authorDateOfBirth;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Book> books;


    public Author(  String authorName, String biography, LocalDate authorDateOfBirth) {
        this.authorName = authorName;
        this.biography = biography;
        this.authorDateOfBirth = authorDateOfBirth;
    }
}
