package com.example.books.service;

import com.example.books.domain.Author;
import com.example.books.repos.AuthorRepo;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepo authorRepo;

    public AuthorService(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    public TreeSet<String> getAuthorsNames(){
        return authorRepo.findAll()
                .stream()
                .map((obj) -> Objects.toString(obj.getAuthorName(), null)).collect(Collectors.toCollection(TreeSet::new));
    }
    public Long findAuthorIdByName(String authorName) {
        return authorRepo.findByAuthorName(authorName).getId();
    }

}
