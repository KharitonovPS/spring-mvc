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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookRepo bookRepo;

    @Autowired
    AuthorRepo authorRepo;
    @GetMapping
    String listBooks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title"));
        Page<Book> books = bookRepo.findAll(pageable);
        List<Author> list = authorRepo.findAll();
        TreeSet<Author> authorSet = new TreeSet<>(list);

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
    ){
        Author author = authorRepo.findByAuthorName(authorName);
        Book book = new Book( title, genre, author);
        bookRepo.save(book);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> books = bookRepo.findAll(pageable);

        model.addAttribute("books", books);

        return "booksList.html";
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
        List<Author> list = authorRepo.findAll();
        TreeSet<Author> authorSet = new TreeSet<>(list);

        model.addAttribute("authorName", authorName);
        model.addAttribute("authorSet", authorSet);
        model.addAttribute("author_id", authorId);

        return "bookDetails.html";
    }

    @PostMapping ("/{id}")
    String changeBook(
            @RequestParam ("title") String title,
            @RequestParam ("genre") BookGenre genre,
            @RequestParam ("authorName") String authorName,
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
        if (authorName !=null && !authorName.isEmpty()){
            updeateBook.setAuthor(authorRepo.findByAuthorName(authorName));
        }
        bookRepo.save(updeateBook);

        List<Book> books = bookRepo.findAll();
        model.addAttribute("books", books);

        return "redirect:/books/{id}";
    }
}
