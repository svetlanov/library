package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksByIdList() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Книга не найдена"));
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    // Поиск книг по нескольким параметрам
    public List<Book> searchBooks(String title, String author, String genre, Integer year) {
        return bookRepository.findByFilters(title, author, genre, year);
    }

    // Добавление новой книги
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }
}
