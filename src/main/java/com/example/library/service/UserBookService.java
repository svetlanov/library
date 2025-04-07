package com.example.library.service;

import com.example.library.model.UserBook;
import com.example.library.model.User;
import com.example.library.model.Book;
import com.example.library.repository.UserBookRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBookService {
    private final UserBookRepository userBookRepository;

    public UserBookService(UserBookRepository userBookRepository) {
        this.userBookRepository = userBookRepository;
    }

    public List<UserBook> getAllUserBooks(User user) {
        return userBookRepository.findByUser(user);
    }

    public UserBook getByUserAndBook(User user, Book book) {
        return userBookRepository.findByUserAndBook(user, book);
    
    }

    public Boolean userHasBook(User user, Book book) {
        return userBookRepository.hasBook(user.getUserId(), book.getId());
    }

    public UserBook addUserBook(User user, Book book) {
        return userBookRepository.save(new UserBook(null, user, book));
    }

    public void removeUserBook(Long id) {
        userBookRepository.deleteById(id);
    }
}
