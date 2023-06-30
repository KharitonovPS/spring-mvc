package com.example.books;

import com.example.books.domain.Author;
import com.example.books.repos.BooksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BooksController {
    @Autowired
    private BooksRepo booksRepo;

    @GetMapping("/authors")
    public String getAuthors(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Author> authors = booksRepo.findAll(pageable);

        model.addAttribute("authors", authors);
        return "authorsPage";
    }

    @GetMapping("/authors/{id}")
    public String getAuthorDetails(@PathVariable("id") int id, Model model) {
        Author author = booksRepo.findById(id);
        model.addAttribute("author", author);
        return "authorDetails";
    }
}