package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.model.UserBook;
import com.example.library.service.BookService;
import com.example.library.service.UserBookService;
import com.example.library.dto.AddBookRequest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserBookController {
    private final UserBookService userBookService;
    private final BookService bookService;

    public UserBookController(UserBookService userBookService, BookService bookService) {
        this.userBookService = userBookService;
        this.bookService = bookService;

    }

    @GetMapping
    public List<Book> getUserBooks(@AuthenticationPrincipal UserDetails userDetails) {
        List<UserBook> userBooks = userBookService.getAllUserBooks((User) userDetails);
        List<Book> books = new ArrayList<>();
        for (UserBook userBook : userBooks) {
            books.add(userBook.getBook());
        }
        return books;
    }

    @PostMapping("/addBook")
    public String addUserBook(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AddBookRequest addBookRequest) {
        Book book = bookService.getBookById(addBookRequest.getBookId());
    
        // Check if user already obtain this book
        Boolean userOwnThisBook = userBookService.userHasBook((User) userDetails, book);

        if (userOwnThisBook) {
            throw new RuntimeException("У пользователя уже есть эта книга");
        }

        UserBook newUserBook =  userBookService.addUserBook((User) userDetails, book);
        return "Пользователю " + newUserBook.getUser().getUsername() + " успешно добавлена книга: " + newUserBook.getBook().getTitle() + "(" + newUserBook.getBook().getId() + ")";
    }

    @PostMapping("/removeBook")
    public String removeUserBook(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AddBookRequest addBookRequest) {
        Book book = bookService.getBookById(addBookRequest.getBookId());
        
        UserBook userBook = userBookService.getByUserAndBook((User) userDetails, book);

        if (userBook == null) {
            throw new RuntimeException("У пользователя нет этой книги");
        }

        userBookService.removeUserBook(userBook.getId());
        return "Пользователю " + userBook.getUser().getUsername() + " успешно удалена книга: " + userBook.getBook().getTitle() + "(" + userBook.getBook().getId() + ")";
    }
}
