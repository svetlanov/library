package com.example.library.repository;

import com.example.library.model.Book;
import com.example.library.model.UserBook;
import com.example.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {
    List<UserBook> findByUser(User user);

    List<UserBook> findByUserId(Long id);

    // Поиск наличия записи о книге у пользователя
    @Query("SELECT CASE WHEN COUNT(ub) > 0 THEN true ELSE false END FROM UserBook ub WHERE ub.user.id = :user_id AND ub.book.id = :book_id")
    Boolean hasBook(@Param("user_id") Long user_id, @Param("book_id") Long book_id);


    UserBook findByUserAndBook(User user, Book book);
}
