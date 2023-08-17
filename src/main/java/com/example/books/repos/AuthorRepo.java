package com.example.books.repos;

import com.example.books.domain.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuthorRepo extends PagingAndSortingRepository<Author, Long>, ListCrudRepository<Author, Long> {

    List<Author> findByAuthorName(String author, Pageable pageable);

    Author  findById(long id);

}
