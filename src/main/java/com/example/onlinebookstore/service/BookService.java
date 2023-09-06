package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.dto.BookSearchParameters;
import com.example.onlinebookstore.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll();

    BookDto getById(Long id);

    void deleteById(Long id);

    BookDto updateById(Long id, CreateBookRequestDto bookRequestDto);

    List<BookDto> search(BookSearchParameters searchParameters);
}
