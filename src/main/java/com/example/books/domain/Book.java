package com.example.books.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

//    @NotBlank(message = "Title may not be blank!")
    private String title;

//    @NotBlank(message = "Please choose 'Other' if you cant find exact genre")
    @Enumerated(EnumType.STRING)
    private BookGenre genre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;

    public String getAuthorName(){
        return author != null ? author.getAuthorName() : "<none>";
    }

    public Book(String title, BookGenre genre, Author author) {
        this.title = title;
        this.genre = genre;
        this.author = author;
    }


}
