package com.example.library.mapper;

import com.example.library.dto.BookDto;
import com.example.library.model.Book;

public class BookMapper {

    public static BookDto toDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setAuthor(book.getAuthor());
        dto.setGenre(book.getGenre());
        dto.setTitle(book.getTitle());
        dto.setYear(book.getYear());
        return dto;
    }
}