package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAll(Pageable pageable);

    Page<Book> findByGenre(String genre, Pageable pageable);

    // Поиск книг по нескольким параметрам
    @Query("SELECT b FROM Book b WHERE " +
            "(:title IS NULL OR b.title LIKE %:title%) AND " +
            "(:author IS NULL OR b.author LIKE %:author%) AND " +
            "(:genre IS NULL OR b.genre LIKE %:genre%) AND " +
            "(:year IS NULL OR b.year = :year)")
    Page<Book> findByFilters(@Param("title") String title, 
                             @Param("author") String author, 
                             @Param("genre") String genre, 
                             @Param("year") Integer year, Pageable pageable);
}

