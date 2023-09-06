package com.example.books.domain.dto;

import com.example.books.domain.BookGenre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class BookDTO{
    private Long id;
    private String title;
    private BookGenre genre;
    private AuthorDTO authorDTO;

    public BookDTO(String title, BookGenre genre, AuthorDTO authorDTO) {
        this.title = title;
        this.genre = genre;
        this.authorDTO = authorDTO;
    }
}
