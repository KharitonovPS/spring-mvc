package com.example.books.controller;

import com.example.books.domain.dto.AuthorDTO;
import com.example.books.domain.dto.BookDTO;
import com.example.books.service.AuthorService;
import com.example.books.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    @GetMapping
    public String getAuthors(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model) {

        Page<AuthorDTO> authors = authorService.getAuthorPages(page, size, "authorName");

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
    ) {
        authorService.addAuthor(authorName, biography, date);
        Page<AuthorDTO> authors = authorService
                .getAuthorPages(0, 5, "authorName");

        model.addAttribute("authors", authors);

        return "authorsList.html";
    }

    @GetMapping("/{id}")
    public String getAuthorDetails(
            @PathVariable("id") long id,
            Model model
    ) {
        AuthorDTO authorDTO = authorService.findAuthorById(id);
        String dateInView = authorDTO.getAuthorDateOfBirth()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        Set<BookDTO> bookSet = bookService.findByAuthorId(id);

        model.addAttribute("author", authorDTO);
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
    ) {

        authorService.updateAuthor(id, authorName, biography, date);

        List<AuthorDTO> authors = authorService.findAll();

        model.addAttribute("authors", authors);

        return "redirect:/authors/{id}";

    }

}