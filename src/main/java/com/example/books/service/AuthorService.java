package com.example.books.service;

import com.example.books.domain.Author;
import com.example.books.domain.dto.AuthorDTO;
import com.example.books.exceptions.ResourceNotFoundException;
import com.example.books.repos.AuthorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.example.books.util.DateParse.dateParse;
import static com.example.books.util.DateParse.isDateParseable;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepo authorRepo;
    private final AuthorDTOMapper authorDTOMapper;

    // вынести в отдельный утил класс
    private Pageable createPageRequestUsing(int page, int size, Sort sort) {
        return PageRequest.of(page, size, sort);
    }

    public Page<AuthorDTO> getAuthorPages(int page, int size, String sort) {
        Pageable pageRequest = createPageRequestUsing(page, size, Sort.by(sort));
        List<Author> authorList = authorRepo.findAll();
        List<AuthorDTO> allAthtorDTOList = authorList
                .stream()
                .map(authorDTOMapper::toDto)
                .toList();

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), allAthtorDTOList.size());

        List<AuthorDTO> pageContent = allAthtorDTOList.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, allAthtorDTOList.size());
    }

    public void addAuthor(String authorName, String biography, String date) {
        LocalDate localDate;
        if (isDateParseable(date)) {
            localDate = dateParse(date);
            AuthorDTO authorDTO = new AuthorDTO(null, authorName, biography, localDate);
            authorRepo.save(authorDTOMapper.toAuthor(authorDTO));
        }
    }

    public TreeSet<String> getAuthorsNames() {
        return authorRepo.findAll()
                .stream()
                .map(authorDTOMapper::toDto)
                .map(AuthorDTO::getAuthorName)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public Long findAuthorIdByName(String authorName) {
        return authorRepo.findByAuthorName(authorName).getId();
    }

    public AuthorDTO findAuthorById(long id) {
        Author author = authorRepo.findById(id);
        return authorDTOMapper.toDto(author);
    }

    public void updateAuthor(long id, String authorName, String biography, String date) {
        Author updateAuthor = authorRepo.findById(id);
        if (updateAuthor == null) {
            throw new ResourceNotFoundException("Can`t find author with current id");
        }
        LocalDate localDate;
        if (isDateParseable(date)) {
            localDate = dateParse(date);
        } else {
            localDate = updateAuthor.getAuthorDateOfBirth();
        }

        AuthorDTO authorDTO = new AuthorDTO(null, authorName, biography, localDate);
        if (authorDTO.getAuthorName() != null && !authorDTO.getAuthorName().isEmpty()) {
            updateAuthor.setAuthorName(authorDTO.getAuthorName());
        }
        if (authorDTO.getBiography() != null && !authorDTO.getBiography().isEmpty()) {
            updateAuthor.setBiography(authorDTO.getBiography());
        }
        if (authorDTO.getAuthorDateOfBirth() != updateAuthor.getAuthorDateOfBirth()) {
            updateAuthor.setAuthorDateOfBirth(authorDTO.getAuthorDateOfBirth());
        }
        authorRepo.save(updateAuthor);
    }

    public List<AuthorDTO> findAll() {
        return authorRepo.findAll()
                .stream()
                .map(authorDTOMapper::toDto)
                .collect(Collectors.toList());
    }

    public AuthorDTO findByAuthorName(String authorName) {
        return  authorDTOMapper.toDto( authorRepo.findByAuthorName(authorName))
                ;
    }
}
