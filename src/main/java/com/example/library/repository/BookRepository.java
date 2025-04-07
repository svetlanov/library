package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByGenre(String genre);

    // Поиск книг по нескольким параметрам
    @Query("SELECT b FROM Book b WHERE " +
            "(:title IS NULL OR b.title LIKE %:title%) AND " +
            "(:author IS NULL OR b.author LIKE %:author%) AND " +
            "(:genre IS NULL OR b.genre LIKE %:genre%) AND " +
            "(:year IS NULL OR b.year = :year)")
    List<Book> findByFilters(@Param("title") String title, 
                             @Param("author") String author, 
                             @Param("genre") String genre, 
                             @Param("year") Integer year);
}

