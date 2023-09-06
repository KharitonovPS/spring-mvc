package com.example.books.service;

import com.example.books.domain.Author;
import com.example.books.domain.dto.AuthorDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthorDTOMapper {

    public AuthorDTO toDto(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getAuthorName(),
                author.getBiography(),
                author.getAuthorDateOfBirth()
        );
    }

    public Author toAuthor(AuthorDTO authorDTO) {
        return new Author(
                authorDTO.getId(),
                authorDTO.getAuthorName(),
                authorDTO.getBiography(),
                authorDTO.getAuthorDateOfBirth()
        );
    }
}
