package com.example.books.controller;

import com.example.books.domain.Author;
import com.example.books.domain.Book;
import com.example.books.repos.AuthorRepo;
import com.example.books.repos.BookRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static com.example.books.service.DateParse.dateParse;
import static com.example.books.service.DateParse.isDateParseable;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private  AuthorRepo authorRepo;

    @Autowired
    private BookRepo bookRepo;

    @GetMapping
    public String getAuthors(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Author> authors = authorRepo.findAll(pageable);

        model.addAttribute("authors", authors);
        return "authorsList.html";
    }

    @SneakyThrows
    @PostMapping
    public String addAuthor(
            @RequestParam String authorName,
            @RequestParam String biography,
            @RequestParam String date,
            Model model
    ){
        LocalDate localDate;
        if (isDateParseable(date)) {
            localDate = dateParse(date);
            Author author = new Author(authorName, biography, localDate);
            authorRepo.save(author);
        }
        Pageable pageable = PageRequest.of(0, 5, Sort.by("authorName"));
        Page<Author> authors = authorRepo.findAll(pageable);

        model.addAttribute("authors", authors);

        return "authorsList.html";
    }
    @GetMapping("/{id}")
    public String getAuthorDetails(
            @PathVariable("id") long id,
            Model model
    ) {
        Author author = authorRepo.findById(id);
        String dateInView = author.getAuthorDateOfBirth()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        Set<Book> bookSet = bookRepo.findByAuthorId(id);

        model.addAttribute("author", author);
        model.addAttribute("dateOfBirth", dateInView);
        model.addAttribute("bookSet", bookSet);


        return "authorDetails.html";
    }
    @SneakyThrows
    @PostMapping("/{id}")
    String replaceAuthor(
            @RequestParam String authorName,
            @RequestParam String biography,
            @RequestParam String date,
            @PathVariable long id,
            Model model
    ){
        Author updateAuthor = authorRepo.findById(id);
        if (authorName != null && !authorName.isEmpty()){
            updateAuthor.setAuthorName(authorName);
        }
        if (biography != null && !biography.isEmpty()){
            updateAuthor.setBiography(biography);
        }
        LocalDate localDate;
        if (isDateParseable(date)) {
            localDate = dateParse(date);
            updateAuthor.setAuthorDateOfBirth(localDate);
        }
        authorRepo.save(updateAuthor);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Author> authors = authorRepo.findAll(pageable);

        model.addAttribute("authors", authors);

        return "redirect:/authors/{id}";

    }

}