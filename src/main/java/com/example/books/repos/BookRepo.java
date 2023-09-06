package com.example.books.repos;

import com.example.books.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {


    Set<Book> findByAuthorId(long id);

    Book findById(long id);
}
