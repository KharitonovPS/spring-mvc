package com.example.books.service;

import com.example.books.domain.Author;
import com.example.books.domain.Book;
import com.example.books.domain.BookGenre;
import com.example.books.domain.dto.AuthorDTO;
import com.example.books.domain.dto.BookDTO;
import com.example.books.exceptions.ResourceNotFoundException;
import com.example.books.repos.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepo bookRepo;
    private final BookDTOMapper bookDTOMapper;
    private final AuthorService authorService;
    private final AuthorDTOMapper authorDTOMapper;

    public List<BookDTO> getAllBooks() {
        return bookRepo.findAll()
                .stream()
                .map(bookDTOMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO findById(Long id) {
        return bookRepo.findById(id)
                .map(bookDTOMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "book with id [%s] not found".formatted(id)
                ));
    }

    private Pageable createPageRequestUsing(int page, int size, Sort sort) {
        return PageRequest.of(page, size, sort);
    }

    public Page<BookDTO> getBookPages(int page, int size, String sort) {
        Pageable pageRequest = createPageRequestUsing(page, size, Sort.by(sort));
        List<Book> bookList = bookRepo.findAll();
        List<BookDTO> allBookList = bookList
                .stream()
                .map(bookDTOMapper::toDTO)
                .toList();

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), allBookList.size());

        List<BookDTO> pageContent = allBookList.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, allBookList.size());
    }

    public void updateBook(String title, BookGenre bookGenre, String authorName, long id) {

        Book updeateBook = bookRepo.findById(id);
        if (updeateBook == null) {
            throw new ResourceNotFoundException("Can`t find book with current id");
        }
        BookDTO bookDTO = new BookDTO(null, title,
                bookGenre,
                authorService.findByAuthorName(authorName)
        );
        if (bookDTO.getTitle() != null && !bookDTO.getTitle().isEmpty()) {
            updeateBook.setTitle(bookDTO.getTitle());
        }
        if (bookDTO.getGenre() != null && !bookDTO.getGenre().toString().isEmpty()) {
            updeateBook.setGenre(bookDTO.getGenre());
        }
        if (authorName != null && !authorName.isEmpty()) {
            updeateBook.setAuthor(authorDTOMapper
                    .toAuthor
                    (bookDTO.getAuthorDTO()));
        }
        bookRepo.save(updeateBook);
    }

    public Set<BookDTO> findByAuthorId(long id) {
        return bookRepo.findByAuthorId(id)
                .stream()
                .map(bookDTOMapper::toDTO)
                .collect(Collectors.toSet());
    }

    public void addBook(String title, BookGenre genre, String authorName) {
        BookDTO book = new BookDTO
                (title,
                        genre,
                        authorService.findByAuthorName(authorName)
                );
        bookRepo.save(bookDTOMapper.toBook(book));
    }
}






