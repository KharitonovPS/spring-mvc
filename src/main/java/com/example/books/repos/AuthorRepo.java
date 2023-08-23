package com.example.books.repos;

import com.example.books.domain.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {

    Author findByAuthorName(String author);

    Author  findById(long id);

}
