package com.example.books.service;

import com.example.books.exceptions.ResourceNotFoundException;
import com.example.books.domain.dto.BookDTO;
import com.example.books.repos.BookRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class BookService {

    @Autowired
    private final BookRepo bookRepo;

        @Autowired
    private final BookDTOMapper bookDTOMapper;

    public List<BookDTO> getAllBooks(){
        return bookRepo.findAll()
                .stream()
                .map(bookDTOMapper)
                .collect(Collectors.toList());
    }
    public BookDTO findById(Long id){
        return bookRepo.findById(id)
                .map(bookDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "book with id [%s] not found".formatted(id)
                ));
    }

    public Page<BookDTO> findAllPages(Pageable pageable){
        return bookRepo.findAll(pageable)
                .map(bookDTOMapper);
    }


}
