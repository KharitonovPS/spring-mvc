package com.example.books.service;

import com.example.books.domain.Author;
import com.example.books.domain.dto.AuthorDTO;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthorDTOMapper {

    private final BookDTOMapper bookDTOMapper;

    public AuthorDTOMapper(BookDTOMapper bookDTOMapper) {
        this.bookDTOMapper = bookDTOMapper;
    }

    public AuthorDTO toDto(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getAuthorName(),
                author.getBiography(),
                author.getAuthorDateOfBirth(),
                author.getBooks()
                        .stream()
                        .map(bookDTOMapper::toDTO)
                        .collect(Collectors.toSet()));
    }

    public Author toAuthor(AuthorDTO authorDTO) {
        return new Author(
                authorDTO.getId(),
                authorDTO.getAuthorName(),
                authorDTO.getBiography(),
                authorDTO.getAuthorDateOfBirth(),
                authorDTO.getBookDTOSet()
                        .stream()
                        .map(bookDTOMapper::toBook)
                        .collect(Collectors.toSet()));
    }
}
