package com.example.books.controller;

import com.example.books.domain.BookGenre;
import com.example.books.domain.dto.BookDTO;
import com.example.books.service.AuthorService;
import com.example.books.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    String getBooks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model
    ) {
        Page<BookDTO> books = bookService.getBookPages(page, size, "Title");
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
        bookService.addBook(title, genre, authorName);
        Page<BookDTO> books = bookService.getBookPages(0, 5, "title");
        model.addAttribute("books", books);

        return "booksList.html";
    }

    @GetMapping("/{id}")
    String getDetails(
            @PathVariable("id") long id,
            Model model
    ) {
        BookDTO bookDTO = bookService.findById(id);
        String authorName = bookDTO.getAuthorDTO().getAuthorName();
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

        bookService.updateBook(title,genre,authorName,id);
        List<BookDTO> books = bookService.getAllBooks();
        model.addAttribute("books", books);

        return "redirect:/books/{id}";
    }
}
