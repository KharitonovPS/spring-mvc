package com.example.books.repos;

import com.example.books.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {

    Author findByAuthorName(String author);

    Author findById(long id);
}
