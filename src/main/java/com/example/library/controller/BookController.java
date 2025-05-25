package com.example.library.controller;

import com.example.library.dto.BookDto;
import com.example.library.model.Book;
import com.example.library.service.BookService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Page<BookDto> getAllBooks(@PageableDefault(size = 5, sort = "title") Pageable pageable) {
        return bookService.getAllBooks(pageable);
    }

    @GetMapping("/genre/{genre}")
    public Page<BookDto> getBooksByGenre(@PathVariable String genre, @PageableDefault(size = 5, sort = "title") Pageable pageable) {
        return bookService.getBooksByGenre(genre, pageable);
    }

    // Общий поиск книг по разным параметрам (жанр, автор, год и название)
    @GetMapping("/search")
    public Page<BookDto> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer year, 
            @PageableDefault(size = 5, sort = "title") Pageable pageable) {
        return bookService.searchBooks(title, author, genre, year, pageable);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }
}
