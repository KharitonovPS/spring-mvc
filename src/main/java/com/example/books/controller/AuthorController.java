package com.example.books.controller;

import com.example.books.domain.Author;
import com.example.books.repos.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class AuthorController {

    @Autowired
    private  AuthorRepo authorRepo;

    @GetMapping("/authors")
    public String getAuthors(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Author> authors = authorRepo.findAll(pageable);

        model.addAttribute("authors", authors);
        return "authorsPage";
    }

    @GetMapping("/authors/{id}")
    public String getAuthorDetails(
            @PathVariable("id") long id,
            Model model
    ) {
        Author author = authorRepo.findById(id);
        model.addAttribute("author", author);
        return "authorDetails";
    }
    @PostMapping("/authors")
    public String addAuthor(
            @RequestParam String authorName,
            @RequestParam String biography,
            @RequestParam String book,
            @RequestParam String date,
            Map<String, Object> model
    ){
        // add try catch? use util to find if string parseable?
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parseDate = LocalDate.parse(date, formatter);
        Author author = new Author(authorName,biography, book, parseDate);
        authorRepo.save(author);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Author> authors = authorRepo.findAll(pageable);

        model.put("authors", authors);

        return "authorsPage";
    }


    @PutMapping("/author/{id}")
    Author replaceAuthor(
            @RequestBody Author newAuthor,
            @PathVariable long id){

        Author updateAuthor = authorRepo.findById(id);

        updateAuthor.setAuthorName(newAuthor.getAuthorName());
        updateAuthor.setAuthorDateOfBirth(newAuthor.getAuthorDateOfBirth());
        updateAuthor.setBiography(newAuthor.getBiography());
        updateAuthor.setBook(newAuthor.getBook());
        return authorRepo.save(updateAuthor);

    }

    @DeleteMapping("/author/{id}")
    void deleteAuthorById(@PathVariable Long id){
        authorRepo.deleteById(id);
    }

}