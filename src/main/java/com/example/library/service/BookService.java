package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.mapper.BookMapper;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

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

    public Page<BookDto> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(BookMapper::toDto);
    }

    public Page<BookDto> getBooksByGenre(String genre, Pageable pageable) {
        return bookRepository.findByGenre(genre, pageable)
                .map(BookMapper::toDto);
    }

    // Поиск книг по нескольким параметрам
    public Page<BookDto> searchBooks(String title, String author, String genre, Integer year, Pageable pageable) {
        return bookRepository.findByFilters(title, author, genre, year, pageable)
                .map(BookMapper::toDto);
    }

    // Добавление новой книги
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }
}
