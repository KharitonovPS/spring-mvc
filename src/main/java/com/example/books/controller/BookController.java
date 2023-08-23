package com.example.books.controller;

import com.example.books.domain.Author;
import com.example.books.domain.Book;
import com.example.books.domain.BookGenre;
import com.example.books.repos.AuthorRepo;
import com.example.books.repos.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookRepo bookRepo;

    @Autowired
    AuthorRepo authorRepo;
    @GetMapping
    String getBooks (
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookRepo.findAll(pageable);
        model.addAttribute("books", books);

        return "booksList";
    }

    @PostMapping
    public String addBook(
            @RequestParam String title,
            @RequestParam BookGenre genre,
            @RequestParam String authorName,
            Map<String, Object> model
    ){
        Author author = authorRepo.findByAuthorName(authorName);
        Book book = new Book( title, genre, author);
        bookRepo.save(book);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> books = bookRepo.findAll(pageable);

        model.put("books", books);
        return "booksList";
    }
    @GetMapping("/{id}")
    String getDetails(
            @PathVariable ("id") long id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model
    ){
        Book book = bookRepo.findById(id);
        String authorName = book.getAuthorName();
        model.addAttribute(book);
        long authorId = book.getAuthor().getId();
        model.addAttribute("authorName", authorName);
        model.addAttribute("author_id", authorId);

        return "bookDetails";
    }

    @PostMapping ("/{id}")
    String changeBook(
            @RequestParam ("title") String title,
            @RequestParam ("genre") BookGenre genre,
            @PathVariable ("id") long id,
            Model model
    ){
        Book updeateBook = bookRepo.findById(id);
        if(title != null && !title.isEmpty()){
            updeateBook.setTitle(title);
        }
        if (genre !=null && !genre.toString().isEmpty()){
            updeateBook.setGenre(genre);
        }
        bookRepo.save(updeateBook);

        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> books = bookRepo.findAll(pageable);

        model.addAttribute("books", books);
        return "redirect:/books/{id}";
    }
}
