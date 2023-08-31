package com.example.books.controller;

import com.example.books.domain.Author;
import com.example.books.domain.Book;
import com.example.books.domain.BookGenre;
import com.example.books.domain.dto.BookDTO;
import com.example.books.service.AuthorService;
import com.example.books.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.TreeSet;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @GetMapping
    String listBooks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title"));
        Page<BookDTO> books = bookService.findAllPages(pageable);
        TreeSet<String> authorSet = authorService.getAuthorsNames();

        model.addAttribute("books", books);
        model.addAttribute("authorSet", authorSet);

        return "booksList.html";
    }

    @PostMapping
    public String addBook(
            @RequestParam String title,
            @RequestParam BookGenre genre,
            @RequestParam String authorName,
            Model model
    ) {
        Author author = authorRepo.findByAuthorName(authorName);
        Book book = new Book(title, genre, author);
        bookRepo.save(book);
        model.addAttribute(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}")
    String getDetails(
            @PathVariable("id") long id,
            Model model
    ) {
        BookDTO bookDTO = bookService.findById(id);
        String authorName = bookDTO.getAuthorName();

        Long authorId = authorService.findAuthorIdByName(authorName);
        TreeSet<String> authorSet = authorService.getAuthorsNames();

        model.addAttribute("book", bookDTO);
        model.addAttribute("authorName", authorName);
        model.addAttribute("author_id", authorId);
        model.addAttribute("authorSet", authorSet);

        return "bookDetails.html";
    }

    @PostMapping("/{id}")
    String changeBook(
            @RequestParam("title") String title,
            @RequestParam("genre") BookGenre genre,
            @RequestParam("authorName") String authorName,
            @PathVariable("id") long id,
            Model model
    ) {
        Book updeateBook = bookRepo.findById(id);
        if (title != null && !title.isEmpty()) {
            updeateBook.setTitle(title);
        }
        if (genre != null && !genre.toString().isEmpty()) {
            updeateBook.setGenre(genre);
        }
        if (authorName != null && !authorName.isEmpty()) {
            updeateBook.setAuthor(authorRepo.findByAuthorName(authorName));
        }
        bookRepo.save(updeateBook);

        List<Book> books = bookRepo.findAll();
        model.addAttribute("books", books);

        return "redirect:/books/{id}";
    }
}
