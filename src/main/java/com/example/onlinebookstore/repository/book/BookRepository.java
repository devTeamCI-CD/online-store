package com.example.onlinebookstore.repository.book;

import com.example.onlinebookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book> {
    @Query("FROM Book b INNER JOIN FETCH b.categories WHERE b.categories = ")
    List<Book> findAllByCategoryId(Long categoryId);
}
