package com.example.onlinebookstore.repository.book;

import com.example.onlinebookstore.dto.BookSearchParameters;
import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.repository.SpecificationBuilder;
import com.example.onlinebookstore.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book, BookSearchParameters> {
    private SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParams) {
        Specification<Book> specification = Specification.where(null);
        if (searchParams.author() != null && searchParams.author().length > 0) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(searchParams.author()));
        }
        if (searchParams.isbn() != null && searchParams.isbn().length > 0) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("isbn")
                    .getSpecification(searchParams.isbn()));
        }
        if (searchParams.title() != null && searchParams.title().length > 0) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(searchParams.title()));
        }
        return specification;
    }
}
