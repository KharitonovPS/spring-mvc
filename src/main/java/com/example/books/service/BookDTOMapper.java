package com.example.books.service;

import com.example.books.domain.Book;
import com.example.books.domain.dto.BookDTO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class BookDTOMapper {

    private final AuthorDTOMapper authorDTOMapper;

    public BookDTOMapper(@Lazy AuthorDTOMapper authorDTOMapper) {
        this.authorDTOMapper = authorDTOMapper;
    }

    public BookDTO toDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getGenre(),
                authorDTOMapper.toDto(book.getAuthor()));
    }

    public Book toBook(BookDTO bookDTO) {
        return new Book(
                bookDTO.getId(),
                bookDTO.getTitle(),
                bookDTO.getGenre(),
                authorDTOMapper.toAuthor(bookDTO.getAuthorDTO()));
    }
}
