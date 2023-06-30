package com.example.books.repos;

import com.example.books.domain.Author;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepo extends PagingAndSortingRepository<Author, Integer> {

//    List<Author> findAuthorsByAuthorName (String name, Pageable pageable);

    Author findById(int id);
}
