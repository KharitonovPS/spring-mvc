package com.example.books.service;

import com.example.books.domain.Book;
import com.example.books.domain.dto.BookDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;
//magic do not touch
@Service
public class BookDTOMapper implements Function<Book, BookDTO> {
    @Override

    public BookDTO apply(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getGenre(),
                book.getAuthor().getAuthorName());
    }
}
